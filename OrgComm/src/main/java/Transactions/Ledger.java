package Transactions;

import com.orgcom.Block;

import java.util.ArrayList;
import java.util.Iterator;

public interface Ledger {

    /**
     * Adds an Order Request to the Ledger.
     *
     * @param productsTransactions the array of ProductTransactions (as OrderRequests) to be added to the Ledger
     * @return the number of productsTransactions added to the Ledger
     */
    int addOrderRequest(ArrayList<ProductsTransaction> productsTransactions);

    /**
     * Registers an Order to the Ledger.
     *
     * @param paymentTransactions the array of PaymentTransactions that allows the OrdersRequests to be registered
     * @return the ordersRequests that were registered
     */
    Iterator<ProductsTransaction> registerOrderInLedger(ArrayList<PaymentTransaction> paymentTransactions);

    /**
     * Getter for an OrderRequest.
     *
     * @param id the orderId that matches the orderRequest to be returned
     * @return the orderRequest or null if not found
     */
    ProductsTransaction getOrderRequest(String id);

    /**
     * Removes an orderRequest.
     *
     * @param id the orderId that matches the orderRequest to be removed
     * @return true if it was removed otherwise false
     */
    boolean removeOrderRequest(String id);

    /**
     * Getter for the number of Orders registered on the Ledger.
     *
     * @return the number of Orders registered on the Ledger
     */
    int getOrdersCounter();

    /**
     * Getter for the Orders on the Ledger.
     *
     * @return the number of Orders registered on the Ledger
     */
    Iterator<Block> ledgerIterator();

    /**
     * Prints the Ledger to the console.
     */
    void printLedger();

    /**
     * Generates de expedition file with all the orders distributed per container.
     *
     * @throws IllegalStateException if there are no orders
     */
    void generateExpeditionFile();

}
