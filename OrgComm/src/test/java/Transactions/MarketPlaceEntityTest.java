package Transactions;

import com.orgcom.BasicTransaction;
import com.orgcom.District;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketPlaceEntityTest {

    MarketPlaceEntity mpe;

    /*
    TC_MPE_1
    -Verify results when instantiated a MarketPlaceEntity with null address
    */
    @Test
    void instantiateMarketPlaceEntityWithNullAddress() {
        assertThrows(Exception.class, () -> new MarketPlaceEntity(
                "Entity1", District.BRAGA, null, "123456789"));
    }

    /*
    TC_MPE_2
    -Verify results when instantiated a MarketPlaceEntity with null vat
    */
    @Test
    void instantiateMarketPlaceEntityWithNullVat() {
        assertThrows(Exception.class, () -> new MarketPlaceEntity(
                "Entity1", District.BRAGA, "Rua das Pombinhas", null));
    }

    /*
    TC_MPE_3
    -Verify results when instantiated a MarketPlaceEntity with empty address
    */
    @Test
    void instantiateMarketPlaceEntityWithEmptyAddress() {
        assertThrows(Exception.class, () -> new MarketPlaceEntity(
                "Entity1", District.BRAGA, "", "123456789"));
    }

    /*
    TC_MPE_4
    -Verify results when instantiated a MarketPlaceEntity with empty vat
    */
    @Test
    void instantiateMarketPlaceEntityWithEmptyVat() {
        assertThrows(Exception.class, () -> new MarketPlaceEntity(
                "Entity1", District.BRAGA, "Rua das Pombinhas", ""));
    }

    /*
    TC_MPE_5
    -Verify the return of method getAddress()
    */
    @Test
    void getMarketPlaceEntityAddress() {
        mpe = new MarketPlaceEntity(
                "Entity1", District.BRAGA, "Rua das Pombinhas", "123456789");
        assertEquals("Rua das Pombinhas", mpe.getAddress());
    }

    /*
    TC_MPE_6
    -Verify the return of method getVat()
    */
    @Test
    void getMarketPlaceEntityVat() {
        mpe = new MarketPlaceEntity(
                "Entity1", District.BRAGA, "Rua das Pombinhas", "123456789");
        assertEquals("123456789", mpe.getVat());
    }

    /*
    TC_MPE_7
    -Verify results when instantiated a MarketPlaceEntity with wrong lenght VAT (!= 9)
    */
    @Test
    void instantiateMarketPlaceEntityWithWrongLenghtVAT() {
        assertThrows(Exception.class, ()-> new MarketPlaceEntity(
                "Entity1", District.BRAGA, "Rua das Pombinhas", "12345"));
    }
}