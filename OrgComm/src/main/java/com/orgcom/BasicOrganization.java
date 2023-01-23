package com.orgcom;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * the BasicOrganization class provides the functionality of a basic organization in the system.
 */
public class BasicOrganization implements Organization {

    private final ArrayList<Block> ledger = new ArrayList<Block>();
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    /**
     * Creates the organization.
     * Must {@link Organization#registerTransactionsInLedger() register} (empty) transactions to create the genesys block.
     */
    public BasicOrganization() {
        this.registerTransactionsInLedger();
    }

    private boolean checkIfTransactionWasAlreadyRegistered(Transaction transaction) {
        Iterator<Block> blockIterator = this.ledgerIterator();

        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            Iterator<Transaction> transactionsIterator = block.getTransactions();
            while (transactionsIterator.hasNext()) {
                if (transactionsIterator.next().getHash().equals(transaction.getHash())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        if (this.getTransaction(transaction) != null || transaction.getTransactionCounter() <= 0
                || this.checkIfTransactionWasAlreadyRegistered(transaction)) {
            return false;
        }

        this.transactions.add(transaction);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        if (this.getTransaction(transaction) == null) {
            return false;
        }

        return this.transactions.remove(transaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transaction getTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        if (this.transactions.indexOf(transaction) < 0) {
            return null;
        }

        return this.transactions.get(this.transactions.indexOf(transaction));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Block getBlock(int index) {
        if (index < 0 || index >= this.ledger.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        return this.ledger.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBlockCount() {
        return this.ledger.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Block getLastBlock() {
        return this.ledger.get(this.ledger.size() - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int registerTransactionsInLedger() {

        if (this.transactions.isEmpty()) {
            return 0;
        }

        int count = 0;
        ArrayList<Transaction> transactionsToBlock = new ArrayList<Transaction>();
        ArrayList<Transaction> transactionsToRemain = new ArrayList<Transaction>();

        for (Transaction transaction : this.transactions) {
            if (transaction.getSender().getTokens() >= 1) {
                transactionsToBlock.add(transaction);

                transaction.getSender().spendToken();
                count++;
            } else {
                transactionsToRemain.add(transaction);
            }
        }

        if (transactionsToBlock.isEmpty()) {
            return 0;
        }

        this.transactions = transactionsToRemain;
        this.ledger.add(new BasicBlock(transactionsToBlock, this.ledger.size() > 0 ? this.getLastBlock().getHash() : "0"));

        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidLedger() {
        for (int i = 0; i < this.ledger.size() - 1; i++) {
            if (this.ledger.get(i).wasTampered() || !this.ledger.get(i).getHash().equals(this.ledger.get(i + 1).getPreviousHash())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns an iterator over elements of type {@link Block Block}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Block> ledgerIterator() {
        return this.ledger.iterator();
    }

    /**
     * Returns an iterator over elements of type {@link Transaction Transaction}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Transaction> transactionIterator() {
        return this.transactions.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printLedger() {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("LEDGER");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Number of blocks: " + this.getBlockCount());
        System.out.println("Ledger is valid: " + this.isValidLedger());
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("BLOCKS:");
        for (Block block : this.ledger) {
            block.print();
        }
        System.out.println("--------------------------------------------------------------------------------------");

        System.out.println("TRANSACTIONS TO BE PROCESSED:");
        for (Transaction transaction : this.transactions) {
            transaction.print();
        }

        System.out.println("--------------------------------------------------------------------------------------");
    }

    /**
     * Returns a string representation of the ledger.
     *
     * @return a string representation of the ledger
     */
    @Override
    public String toString() {
        return "BasicOrganization{" +
                "ledger=" + this.ledger +
                ", transactions=" + this.transactions +
                '}';
    }
}
