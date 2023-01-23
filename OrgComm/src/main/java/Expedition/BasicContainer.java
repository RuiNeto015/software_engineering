package Expedition;

import Transactions.ProductsTransaction;
import com.orgcom.Block;
import com.orgcom.District;
import com.orgcom.Transaction;

import java.util.ArrayList;
import java.util.Iterator;

public class BasicContainer {
    private int MAX_SIZE = 63;
    private ArrayList<Block> orders;
    private District district;
    private float usedSpace;

    /**
     * BasicContainer class constructor.
     *
     * @param district the container district
     */
    public BasicContainer(District district) {
        this.orders = new ArrayList<>();
        this.usedSpace = 0;
        this.district = district;
    }

    /**
     * Getter for the usedSpace on the container.
     *
     * @return the usedSpace on the container
     */
    public float getUsedSpace() {
        return this.usedSpace;
    }

    /**
     * Getter for the destination District of the container.
     *
     * @return the destination District of the container.
     */
    public District getDistrict() {
        return this.district;
    }

    private boolean verifyDistrict(String district) {
        return District.valueOf(district).equals(this.district);
    }

    /**
     * Adds an Order to the BasicContainer.
     *
     * @param order the order to be added
     * @return 0 if the order destination district doesn't match the container destination district, 1 if inserted, and
     * -1 if the container doesn't have space.
     */
    public int addOrder(Block order) {
        Iterator<Transaction> transactionIterator = order.getTransactions();
        ProductsTransaction productsTransaction = (ProductsTransaction) transactionIterator.next();

        if (!verifyDistrict(productsTransaction.getReceiver().getDistrict().toUpperCase())) {
            return 0;
        }
        if (productsTransaction.getVolume() + this.usedSpace <= this.MAX_SIZE) {
            this.orders.add(order);
            this.usedSpace += productsTransaction.getVolume();
            return 1;
        }
        return -1;
    }

    /**
     * Getter for the orders inside the container.
     *
     * @return the orders iterator
     */
    public Iterator<Block> getOrders() {
        return this.orders.iterator();
    }
}
