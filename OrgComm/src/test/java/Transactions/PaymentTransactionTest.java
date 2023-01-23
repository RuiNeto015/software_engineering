package Transactions;

import com.orgcom.BasicTransactionLine;
import com.orgcom.District;
import com.orgcom.TransactionLine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTransactionTest {

    /*
    TC_PAYT_1
    -Verify results when instantiated a PaymentTransaction with null sender
    */
    @Test
    void PaymentTransactionWithNullSender() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = null;
        assertThrows(Exception.class, () -> new PaymentTransaction(mpe2, mpe1,"ID1"));
    }

    /*
    TC_PAYT_2
    -Verify results when instantiated a PaymentTransaction with null receiver
    */
    @Test
    void PaymentTransactionWithNullReceiver() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = null;
        assertThrows(Exception.class, () -> new PaymentTransaction(mpe1, mpe2,"ID1"));
    }

    /*
    TC_PAYT_3
    -Verify results when added more than one transaction line to the PaymentTransaction
    */
    @Test
    void PaymentTransactionWithMoreThanOneTransactionLine() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = new MarketPlaceEntity("Entity 2", District.COIMBRA,
                "Rua das Portelas", "987654321");
        PaymentTransaction payt = new PaymentTransaction(mpe1, mpe2,"ID1");
        payt.addTransactionLine(new BasicTransactionLine("EUROS", 10, 1));
        BasicTransactionLine btl1 = new BasicTransactionLine("EUROS", 10, 1);
        assertEquals(false, payt.addTransactionLine(btl1));
    }

    /*
    TC_PAYT_4
    -Verify the return of method getOrderId()
    */
    @Test
    void getOrderId() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = new MarketPlaceEntity("Entity 2", District.COIMBRA,
                "Rua das Portelas", "987654321");
        PaymentTransaction payt = new PaymentTransaction(mpe1, mpe2,"ID1");
        assertEquals("ID1", payt.getOrderId());
    }
}