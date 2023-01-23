package Transactions;

import com.orgcom.BasicTransaction;
import com.orgcom.TransactionLine;

public class PaymentTransaction extends BasicTransaction {

    private String orderId;

    /**
     * Constructor for PaymentTransaction.
     * The transaction is created with the date of creation, the sender and the receiver.
     *
     * @param sender   the sender of the transaction.
     * @param receiver the receiver of the transaction.
     * @param orderId the id of the transaction.
     * @throws IllegalArgumentException if the sender or receiver is null.
     */
    public PaymentTransaction(MarketPlaceEntity sender, MarketPlaceEntity receiver, String orderId) {
        super(sender, receiver);

        if(orderId.isEmpty()){
            throw new IllegalArgumentException("OrderId is null");
        }
        this.orderId = orderId;
    }

    /**
     * Adds a {@link TransactionLine transaction line} to the transaction.
     * When the {@link TransactionLine transaction line} is added to the transaction, the total value and the
     * transaction counter are updated.
     *
     * @param transactionLine the {@link TransactionLine transaction line} to be added.
     * @return true if the transaction line was added, false if the transaction line was already added or the counter
     * is higher than zero.
     * @throws IllegalArgumentException if the transaction line is null.
     */
    @Override
    public boolean addTransactionLine(TransactionLine transactionLine) {
        if(super.getTransactionCounter() > 0){
            return false;
        }
        return super.addTransactionLine(transactionLine);
    }

    /**
     * Getter for the matching orderId.
     *
     * @return the matching orderId
     */
    public String getOrderId() {
        return orderId;
    }
}