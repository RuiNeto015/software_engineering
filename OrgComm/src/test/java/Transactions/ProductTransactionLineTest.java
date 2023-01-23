package Transactions;

import com.orgcom.District;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTransactionLineTest {

    ProductTransactionLine ptl;

    /*
    TC_PTL_1
    -Verify results when instantiated a ProductTransactionLine with negative volume
    */
    @Test
    void instantiateProductTransactionLineWithNegativeVolume() {
        assertThrows(Exception.class, () -> new ProductTransactionLine("P1", 5,
                5, -15, 10));
    }

    /*
    TC_PTL_2
    -Verify results when instantiated a ProductTransactionLine with volume = 0
    */
    @Test
    void instantiateProductTransactionLineWithVolume0() {
        assertThrows(Exception.class, () -> new ProductTransactionLine("P1", 5,
                5, 0, 10));
    }

    /*
    TC_PTL_3
    -Verify results when instantiated a ProductTransactionLine with negative weight
    */
    @Test
    void instantiateProductTransactionLineWithNegativeWeight() {
        assertThrows(Exception.class, () -> new ProductTransactionLine("P1", 5,
                5, 15, -10));
    }

    /*
    TC_PTL_4
    -Verify results when instantiated a ProductTransactionLine with weight = 0
    */
    @Test
    void instantiateProductTransactionLineWithWeight0() {
        assertThrows(Exception.class, () -> new ProductTransactionLine("P1", 5,
                5, 15, 0));
    }

    /*
    TC_PTL_5
    -Verify the return of method getVolume()
    */
    @Test
    void getProductTransactionLineVolume() {
        this.ptl = new ProductTransactionLine("P1", 5, 5.0D, 15.0F, 10.0F);
        assertEquals(15.0F, this.ptl.getVolume());
    }

    /*
    TC_PTL_6
    -Verify the return of method getWeight()
    */
    @Test
    void getProductTransactionLineWeight() {
        this.ptl = new ProductTransactionLine("P1", 5, 5.0D, 15.0F, 10.0F);
        assertEquals(10.0F, this.ptl.getWeight());
    }
}