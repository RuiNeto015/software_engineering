package Transactions;

import CostCalculation.BasicShippingFees;
import CostCalculation.ShippingFees;
import Expedition.BasicContainer;
import Expedition.BasicContainersHandling;
import com.orgcom.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MarketPlaceLedger implements Ledger, Statistics {

    private Organization org;
    private ArrayList<ProductsTransaction> orderRequests;
    private ShippingFees shippingFees;

    /**
     * Creates a new Ledger.
     */
    public MarketPlaceLedger() {
        this.org = new BasicOrganization();
        this.orderRequests = new ArrayList<>();
        this.shippingFees = new BasicShippingFees(".\\ShippingFeesConfig\\distances.csv",
                ".\\ShippingFeesConfig\\fees.config");
    }

    private boolean orderRequestExists(String orderID){
        Iterator<ProductsTransaction> orderRequest = this.orderRequests.iterator();
        while(orderRequest.hasNext()){
            ProductsTransaction productsTransaction = orderRequest.next();
            if(productsTransaction.getOrderId().equals(orderID)){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds Order Requests to the ledger. If the orderId already exists the order isn't added.
     *
     * @param productsTransactions the array of ProductTransactions (as OrderRequests) to be added to the Ledger
     * @return the number of productsTransactions added to the Ledger
     */
    public int addOrderRequest(ArrayList<ProductsTransaction> productsTransactions) {
        int count = 0;

        for (int i = 0; i < productsTransactions.size(); i++) {
            if (productsTransactions.get(i).getTransactionCounter() > 0 &&
                    !this.orderRequestExists(productsTransactions.get(i).getOrderId())) {

                this.orderRequests.add(productsTransactions.get(i));
                count++;
            }
        }
        return count;
    }

    private boolean PaymentValueIsCovered(PaymentTransaction paymentTransaction, ProductsTransaction
            productsTransaction) {

        String start = productsTransaction.getSender().getDistrict().toUpperCase();
        String destination = productsTransaction.getReceiver().getDistrict().toUpperCase();

        return paymentTransaction.getTotalValue() >= productsTransaction.getTotalValue()
                + shippingFees.getShippingFee(District.valueOf(start), District.valueOf(destination),
                productsTransaction.getWeight(), productsTransaction.getVolume());
    }

    private ProductsTransaction findOrderRequestById(String id) {
        for (int i = 0; i < this.orderRequests.size(); i++) {
            if (id.equals(this.orderRequests.get(i).getOrderId())) {
                return this.orderRequests.get(i);
            }
        }
        return null;
    }

    /**
     * Registers an Order to the Ledger.
     *
     * @param paymentTransactions the array of PaymentTransactions that allows the OrdersRequests to be registered
     * @return the ordersRequests that were registered
     */
    public Iterator<ProductsTransaction> registerOrderInLedger(ArrayList<PaymentTransaction> paymentTransactions) {
        ArrayList<ProductsTransaction> successfullyPaid = new ArrayList<>();

        for (int i = 0; i < paymentTransactions.size(); i++) {
            ProductsTransaction productsTransaction = this.findOrderRequestById(paymentTransactions.get(i).getOrderId());
            if (productsTransaction != null && this.PaymentValueIsCovered(paymentTransactions.get(i),
                    productsTransaction) && paymentTransactions.get(i).getSender().getName().equals(productsTransaction
                    .getReceiver().getName())) {

                this.orderRequests.remove(productsTransaction);
                productsTransaction.getSender().addTokens(1);
                paymentTransactions.get(i).getSender().addTokens(1);
                this.org.addTransaction(productsTransaction);
                this.org.addTransaction(paymentTransactions.get(i));
                this.org.registerTransactionsInLedger();
                successfullyPaid.add(productsTransaction);
            }
        }
        return successfullyPaid.iterator();
    }

    /**
     * Getter for an OrderRequest.
     *
     * @param id the orderId that matches the orderRequest to be returned
     * @return the orderRequest or null if not found
     */
    public ProductsTransaction getOrderRequest(String id) {
        return this.findOrderRequestById(id);
    }

    /**
     * Removes an orderRequest.
     *
     * @param id the orderId that matches the orderRequest to be removed
     * @return true if it was removed otherwise false
     */
    public boolean removeOrderRequest(String id) {
        return this.orderRequests.remove(this.findOrderRequestById(id));
    }

    /**
     * Getter for the number of Orders registered on the Ledger.
     *
     * @return the number of Orders registered on the Ledger
     */
    public int getOrdersCounter() {
        return this.org.getBlockCount();
    }

    /**
     * Getter for the Orders on the Ledger.
     *
     * @return the number of Orders registered on the Ledger
     */
    public Iterator<Block> ledgerIterator() {
        return this.org.ledgerIterator();
    }

    private void printOrder(Block block) {
        Iterator<Transaction> transactionIterator = block.getTransactions();
        ProductsTransaction productsTransaction = (ProductsTransaction) transactionIterator.next();
        PaymentTransaction paymentTransaction = (PaymentTransaction) transactionIterator.next();

        System.out.println("\nID: " + productsTransaction.getOrderId() + "{" + productsTransaction.getDateCreated() + "}");
        System.out.println("Hash: " + block.getHash() + "{" + block.getDate() + "}");
        System.out.println("PreviousOrderHash: " + block.getPreviousHash());
        System.out.println("Transactions: ");
        System.out.println("ProductsTransaction-> " + productsTransaction.toString());
        System.out.print("PaymentTransaction-> ");
        paymentTransaction.print();
    }

    private void printOrderRequest(ProductsTransaction productsTransaction) {
        System.out.println("orderId: " + productsTransaction.getOrderId() + " creationDate: "
                + productsTransaction.getDateCreated() + " " + productsTransaction.toString());
    }

    private void printPerDistrict(Map<District, Float> sales, Map<District, Float> purchases) {
        for (District district : District.values()) {
            System.out.println(" -" + district + ": " + sales.get(district) + "; " + purchases.get(district));
        }
    }

    /**
     * Prints the Ledger to the console.
     */
    public void printLedger() {
        Iterator<Block> blockIterator = this.org.ledgerIterator();

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("LEDGER");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Number of blocks: " + this.org.getBlockCount());
        System.out.println("Ledger is valid: " + this.org.isValidLedger());
        System.out.println("Average Transactions value: " + this.averageTransactionsValue());
        System.out.println("Average Number of Products per Transaction: " + this.averageNumberOfProductsPerTransaction());
        System.out.println("Average Value Of Sales per District:");
        this.printPerDistrict(this.averageValueOfSalesPerDistrict(), this.averageValueOfPurchasesPerDistrict());
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("ORDERS:");
        while (blockIterator.hasNext()) {
            this.printOrder(blockIterator.next());
        }
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("ORDER REQUESTS");
        for (int i = 0; i < this.orderRequests.size(); i++) {
            printOrderRequest(this.orderRequests.get(i));
        }

        System.out.println("--------------------------------------------------------------------------------------");
    }

    /**
     * Calculates the average transactions value.
     *
     * @return the average transactions value
     */
    @Override
    public float averageTransactionsValue() {
        Iterator<Block> blocks = this.ledgerIterator();
        float total = 0;

        while (blocks.hasNext()) {
            Block block = blocks.next();
            Iterator<Transaction> transactionIterator = block.getTransactions();
            ProductsTransaction productsTransaction = (ProductsTransaction) transactionIterator.next();
            total += productsTransaction.getTotalValue();
        }

        if (total == 0) {
            return 0;
        }
        return total / this.getOrdersCounter();
    }

    /**
     * Calculates the average number of products per transaction.
     *
     * @return the average number of products per transaction
     */
    @Override
    public float averageNumberOfProductsPerTransaction() {
        Iterator<Block> blocks = this.ledgerIterator();
        int total = 0;

        while (blocks.hasNext()) {
            Block block = blocks.next();
            Iterator<Transaction> transactionIterator = block.getTransactions();
            ProductsTransaction productsTransaction = (ProductsTransaction) transactionIterator.next();
            Iterator<TransactionLine> transactionLineIterator = productsTransaction.iterator();
            while (transactionLineIterator.hasNext()) {
                total += transactionLineIterator.next().getQuantity();
            }
        }

        if (total == 0) {
            return 0;
        }
        return (float) total / this.org.getBlockCount();
    }

    private float averageValueOfSales(District district) {
        Iterator<Block> blocks = this.ledgerIterator();
        int total = 0;
        int salesCounter = 0;

        while (blocks.hasNext()) {
            Block block = blocks.next();
            Iterator<Transaction> transactionIterator = block.getTransactions();
            ProductsTransaction productsTransaction = (ProductsTransaction) transactionIterator.next();
            if (District.valueOf(productsTransaction.getSender().getDistrict().toUpperCase()) == district) {
                total += productsTransaction.getTotalValue();
                salesCounter++;
            }
        }

        if (salesCounter == 0) {
            return 0;
        }
        return (float) total / salesCounter;
    }

    /**
     * Calculates the average value of sales per district.
     *
     * @return the average value of sales per district
     */
    @Override
    public Map<District, Float> averageValueOfSalesPerDistrict() {
        Map<District, Float> averages = new HashMap<>();

        for (District district : District.values()) {
            averages.put(district, this.averageValueOfSales(district));
        }
        return averages;
    }

    private float averageValueOfPurchases(District district) {
        Iterator<Block> blocks = this.ledgerIterator();
        int total = 0;
        int purchasesCounter = 0;

        while (blocks.hasNext()) {
            Block block = blocks.next();
            Iterator<Transaction> transactionIterator = block.getTransactions();
            ProductsTransaction productsTransaction = (ProductsTransaction) transactionIterator.next();
            if (District.valueOf(productsTransaction.getReceiver().getDistrict().toUpperCase()) == district) {
                total += productsTransaction.getTotalValue();
                purchasesCounter++;
            }
        }

        if (purchasesCounter == 0) {
            return 0;
        }
        return (float) total / purchasesCounter;
    }

    /**
     * Calculates the average value of purchases per district.
     *
     * @return the average value of purchases per district
     */
    @Override
    public Map<District, Float> averageValueOfPurchasesPerDistrict() {
        Map<District, Float> averages = new HashMap<>();

        for (District district : District.values()) {
            averages.put(district, this.averageValueOfPurchases(district));
        }
        return averages;
    }

    private void ordersGroupedByDistrictAndContainer(BasicContainersHandling containersHandling, District district) {
        Iterator<Block> blocks = this.ledgerIterator();

        BasicContainer container = new BasicContainer(district);
        containersHandling.addContainer(container);
        while (blocks.hasNext()) {
            Block block = blocks.next();
            int result = container.addOrder(block);

            if (result < 0) {
                container = new BasicContainer(district);
                containersHandling.addContainer(container);
            }
        }
    }

    /**
     * Generates de expedition file with all the orders distributed per container.
     *
     * @throws IllegalStateException if there are no orders
     */
    public void generateExpeditionFile() {
        if (this.org.getBlockCount() == 0) {
            throw new IllegalStateException("There are no orders at the moment.");
        }
        BasicContainersHandling containersHandling = new BasicContainersHandling("./expedition.json");

        for (District district : District.values()) {
            this.ordersGroupedByDistrictAndContainer(containersHandling, district);
        }
        containersHandling.exportToJson();
    }
}
