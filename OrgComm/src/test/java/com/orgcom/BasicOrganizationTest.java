package com.orgcom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicOrganizationTest {

    private Organization org;
    private Entity e1;
    private Entity e2;
    private Transaction t;

    @BeforeEach
    void setup() {
        this.org = new BasicOrganization();
        this.e1 = new BasicEntity("e1", District.BRAGA);
        this.e2 = new BasicEntity("e2", District.BRAGA);
        this.t = new BasicTransaction(e1, e2);
    }

    /*
    TC_BasicoOrg_2
    -Verify results adding a new Transaction with 0 TransactionLine
     */
    @Test
    void addTransactionWithZeroTransactionLines() {
        assertEquals(false, this.org.addTransaction(this.t));
    }

    /*
    TC_BasicOrg_5
    -Verify results adding the same Transaction multiple times
     */
    @Test
    void addTheSameTransactionMultipleTimes() {
        this.e1.addTokens(1);
        this.t.addTransactionLine(new BasicTransactionLine("cadeiras", 10, 1));
        this.org.addTransaction(this.t);
        this.org.registerTransactionsInLedger();
        assertEquals(false, this.org.addTransaction(this.t));
    }

    /*
    TC_BasicOrg_8
    -Verify results removing an already registered Transaction
     */
    @Test
    void removeARegisteredTransaction(){
        this.org.addTransaction(t);
        this.t.addTransactionLine(new BasicTransactionLine("cadeiras", 5,10));
        this.e1.addTokens(1);
        this.org.registerTransactionsInLedger();
        assertEquals(false, this.org.removeTransaction(t));
    }
}