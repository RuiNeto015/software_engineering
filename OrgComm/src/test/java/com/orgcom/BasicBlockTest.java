package com.orgcom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BasicBlockTest {

    private ArrayList<Transaction> transactions;

    @BeforeEach
    void setup(){
        this.transactions = new ArrayList<>();
    }

    /*
    TC_BasicBlock_1
    -Verify if an exception is thrown with an empty TransactionsList
     */
    @Test
    void constructorWithEmptyTransactionsList(){
        assertThrows(Exception.class, () -> new BasicBlock(this.transactions, "helloWorld"));
    }

    /*
    TC_BasicBlock_4
    -Verify if an execption is thrown with an empty hash
     */
    @Test
    void constructorWithEmptyHash(){
        this.transactions.add(new BasicTransaction(new BasicEntity("e1", District.BRAGA),
                new BasicEntity("e2", District.BRAGA)));

        assertThrows(Exception.class, () -> new BasicBlock(this.transactions, ""));
    }

    /*
    TC_BasicBlock_5
    -Verify if an execption is thrown a null argument
     */
    @Test
    void constructorWithNullArguments(){
        this.transactions.add(new BasicTransaction(new BasicEntity("e1", District.BRAGA),
                new BasicEntity("e2", District.BRAGA)));

        assertThrows(Exception.class, () -> new BasicBlock(null, "helloWorld"));
        assertThrows(Exception.class, () -> new BasicBlock(null, null));
        assertThrows(Exception.class, () -> new BasicBlock(this.transactions, null));
    }

}