package Transactions;

import com.orgcom.BasicTransaction;
import com.orgcom.TransactionLine;

import java.util.ArrayList;
import java.util.Iterator;

public class ProductsTransaction extends BasicTransaction {

    private String orderId;

    /**
     * Constructor for ProductsTransaction.
     * The ProductsTransaction is created with the date of creation, the sender and the receiver.
     *
     * @param sender   the sender of the transaction.
     * @param receiver the receiver of the transaction.
     * @param orderId the id of the transaction.
     * @throws IllegalArgumentException if the sender or receiver is null.
     */
    public ProductsTransaction(MarketPlaceEntity sender, MarketPlaceEntity receiver, String orderId) {
        super(sender, receiver);
        this.orderId = orderId;
    }

    /**
     * Adds a {@link TransactionLine transaction line} to the transaction.
     * When the {@link TransactionLine transaction line} is added to the transaction, the total value and the
     * transaction counter are updated.
     *
     * @param transactionLine the {@link TransactionLine transaction line} to be added.
     * @return true if the transaction line was added, false if the transaction line was already added.
     * @throws IllegalArgumentException if the transaction line is null or isn't an instance
     *                                  of {@link ProductTransactionLine product transaction line}.
     */
    @Override
    public boolean addTransactionLine(TransactionLine transactionLine) {
        if (!(transactionLine instanceof ProductTransactionLine)) {
            throw new IllegalArgumentException("Incompatible type for transactionLine");
        }
        return super.addTransactionLine(transactionLine);
    }

    private Iterator<ProductTransactionLine> castIterator() {
        Iterator<TransactionLine> transactionLineIterator = super.iterator();
        ArrayList<ProductTransactionLine> productTransactionLines = new ArrayList<>();

        while (transactionLineIterator.hasNext()) {
            productTransactionLines.add((ProductTransactionLine) transactionLineIterator.next());
        }
        return productTransactionLines.iterator();
    }

    /**
     * Getter for the Transaction's total volume.
     *
     * @return the product's total volume.
     */
    public float getVolume() {
        Iterator<ProductTransactionLine> transactionLineIterator = this.castIterator();
        float volume = 0;

        while (transactionLineIterator.hasNext()) {
            volume += transactionLineIterator.next().getVolume();
        }
        return volume;
    }

    /**
     * Getter for the Transaction's total weight.
     *
     * @return the Transaction's total weight.
     */
    public float getWeight() {
        Iterator<ProductTransactionLine> transactionLineIterator = this.castIterator();
        float weight = 0;

        while (transactionLineIterator.hasNext()) {
            weight += transactionLineIterator.next().getWeight();
        }
        return weight;
    }

    /**
     * Converts this object to String.
     *
     * @return the object String
     */
    public String toString() {
        return " hash: " + super.getHash() + " value: " + super.getTotalValue() + " sender: " + super.getSender()
                .getName() + " receiver:" + super.getReceiver().getName() + " volume:" + this.getVolume() + " weight:"
                + this.getWeight();
    }

    /**
     * Getter for the matching orderId.
     *
     * @return the matching orderId
     */
    public String getOrderId() {
        return this.orderId;
    }
}
