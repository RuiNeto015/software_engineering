package Transactions;

import com.orgcom.BasicTransactionLine;
import com.orgcom.District;
import com.orgcom.TransactionLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductsTransactionTest {

    /*
    TC_PT_1
    -Verify results when instantiated a ProductTransaction with null sender
    */
    @Test
    void ProductTransactionWithNullSender() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = null;
        assertThrows(Exception.class, () -> new ProductsTransaction(mpe2, mpe1,"ID1"));
    }

    /*
    TC_PT_2
    -Verify results when instantiated a ProductTransaction with null receiver
    */
    @Test
    void ProductTransactionWithNullReceiver() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = null;
        assertThrows(Exception.class, () -> new ProductsTransaction(mpe1, mpe2, "ID1"));
    }

    /*
   TC_PT_3
   -Verify the return of addTransaction() when the paremeter is not instance of ProductTransactionLine
   */
    @Test
    void addTransactionParemeterIsNotInstanceOfProductTransactionLine() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = new MarketPlaceEntity("Entity 2", District.COIMBRA,
                "Rua das Portas", "987654321");
        ProductsTransaction pt = new ProductsTransaction(mpe1, mpe2,"ID1");
        TransactionLine tl = new BasicTransactionLine("P1", 5, 5);
        assertThrows(Exception.class, () -> pt.addTransactionLine(tl));
    }

    /*
   TC_PT_4
   -Verify the return of method getVolume with 1 ProductTransactionLine
   */
    @Test
    void getVolumeWith1ProductTransactionLine() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = new MarketPlaceEntity("Entity 2", District.COIMBRA,
                "Rua das Portas", "987654321");
        ProductsTransaction pt = new ProductsTransaction(mpe1, mpe2, "ID1");
        ProductTransactionLine ptl = new ProductTransactionLine("P1", 1,
                3, 7, 10);
        pt.addTransactionLine(ptl);
        assertEquals(7, pt.getVolume());
    }

    /*
   TC_PT_5
   -Verify the return of method getVolume with 2 ProductTransactionLine
   */
    @Test
    void getVolumeWith2ProductTransactionLine() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = new MarketPlaceEntity("Entity 2", District.COIMBRA,
                "Rua das Portas", "987654321");
        ProductsTransaction pt = new ProductsTransaction(mpe1, mpe2,"ID1");
        ProductTransactionLine ptl1 = new ProductTransactionLine("P1", 1,
                3, 7, 10);
        ProductTransactionLine ptl2 = new ProductTransactionLine("P2", 2,
                3, 7, 10);
        pt.addTransactionLine(ptl1);
        pt.addTransactionLine(ptl2);
        assertEquals(14, pt.getVolume());
    }

    /*
   TC_PT_6
   -Verify the return of method getWeight with 1 ProductTransactionLine
   */
    @Test
    void getWeightWith1ProductTransactionLine() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = new MarketPlaceEntity("Entity 2", District.COIMBRA,
                "Rua das Portas", "987654321");
        ProductsTransaction pt = new ProductsTransaction(mpe1, mpe2, "ID1");
        ProductTransactionLine ptl1 = new ProductTransactionLine("P1", 2,
                3, 7, 10);
        pt.addTransactionLine(ptl1);
        assertEquals(10, pt.getWeight());
    }

    /*
   TC_PT_7
   -Verify the return of method getWeight with 2 ProductTransactionLine
   */
    @Test
    void getWeightWith2ProductTransactionLine() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = new MarketPlaceEntity("Entity 2", District.COIMBRA,
                "Rua das Portas", "987654321");
        ProductsTransaction pt = new ProductsTransaction(mpe1, mpe2, "ID1");
        ProductTransactionLine ptl1 = new ProductTransactionLine("P1", 2,
                3, 7, 10);
        ProductTransactionLine ptl2 = new ProductTransactionLine("P2", 2,
                3, 7, 10);
        pt.addTransactionLine(ptl1);
        pt.addTransactionLine(ptl2);
        assertEquals(20, pt.getWeight());
    }

    /*
   TC_PT_8
   -Verify the return of method getOrderId
   */
    @Test
    void getOrderId() {
        MarketPlaceEntity mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        MarketPlaceEntity mpe2 = new MarketPlaceEntity("Entity 2", District.COIMBRA,
                "Rua das Portas", "987654321");
        ProductsTransaction pt = new ProductsTransaction(mpe1, mpe2, "ID1");
        assertEquals("ID1", pt.getOrderId());
    }
}