package com.orgcom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicTransactionTest {

    private Organization org;
    private Entity e1;
    private Transaction t;

    @BeforeEach
    void setup() {
        this.org = new BasicOrganization();
        this.e1 = new BasicEntity("e1", District.BRAGA);
    }

    /*
    TC_BasicT_1
    -Verify results when instantiated a Basic transaction with the same sender and receiver
     */
    @Test
    void instantiateBasicTransactionWithSameSenderAndReceiver() {
        assertThrows(Exception.class, () -> this.t = new BasicTransaction(e1, e1));
    }
}