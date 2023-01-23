package CostCalculation;

import static org.junit.jupiter.api.Assertions.*;

class BasicShippingFeesTest {
/*
    TC_BSF_1
    -Verify the Constructor method result when instantiated with nonexistent filepath in the first parameter
    @Test
    void BasicShippingFeesWithFirstParameterNonexistentFilePath() {
        assertThrows(Exception.class, () -> new BasicShippingFees(".\\ShippingFeesConfig\\distancess.csv",
                ".\\ShippingFeesConfig\\fees.config"));
    }
    */

    /*
    TC_BSF_2
    -Verify the Constructor method result when instantiated with nonexistent filepath in the second parameter
    @Test
    void BasicShippingFeesWithSecondParameterNonexistentFilePath() {
        assertThrows(Exception.class, () -> new BasicShippingFees(".\\ShippingFeesConfig\\distances.csv",
                ".\\ShippingFeesConfig\\feess.config"));
    }
    */

    /*
    TC_BSF_3
    -Verify getShippingFee() method result when the districts in the transaction are the same
    @Test
    void getShippingFeeSameDistrict() {
        BasicShippingFees bsf = new BasicShippingFees(".\\ShippingFeesConfig\\distances.csv",
                ".\\ShippingFeesConfig\\fees.config");

        assertEquals(1.5, bsf.getShippingFee(District.BRAGA, District.BRAGA, 10 , 10));
    }
    */

    /*
    TC_BSF_4
    -Verify getShippingFee() method result when the weight is 0
    @Test
    void getShippingFee0Weight() {
        BasicShippingFees bsf = new BasicShippingFees(".\\ShippingFeesConfig\\distances.csv",
                ".\\ShippingFeesConfig\\fees.config");

        assertEquals(1.5, bsf.getShippingFee(District.BRAGA, District.COIMBRA, 0, 10));
    }
    */

    /*
    TC_BSF_5
    -Verify getShippingFee() method result when the volume is 0
    @Test
    void getShippingFee0Volume() {
        BasicShippingFees bsf = new BasicShippingFees(".\\ShippingFeesConfig\\distances.csv",
                ".\\ShippingFeesConfig\\fees.config");

        assertEquals(2.5, bsf.getShippingFee(District.VIANA_CASTELO, District.BRAGA, 600 , 0));
    }
    */
}