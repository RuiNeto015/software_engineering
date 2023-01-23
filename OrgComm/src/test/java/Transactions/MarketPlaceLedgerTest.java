package Transactions;

import com.orgcom.BasicTransactionLine;
import com.orgcom.District;
import com.orgcom.TransactionLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MarketPlaceLedgerTest {

    MarketPlaceLedger mpl;
    MarketPlaceEntity mpe1, mpe2;

    @BeforeEach
    public void setup() {
        mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        mpe2 = new MarketPlaceEntity("Entity 2", District.COIMBRA,
                "Portela Grande", "987654321");
        mpl = new MarketPlaceLedger();
    }

    /*
    TC_MPL_1
    -Verify results of method addOrderRequest() with no productsTransaction to add
    */
    @Test
     void addOrderRequestWithNoProductsTransactionToAdd() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        mpl.addOrderRequest(alpt);
        assertEquals(0, mpl.addOrderRequest(alpt));
    }

    /*
    TC_MPL_2
    -Verify results of method addOrderRequest() with one productsTransaction to add
    */
    @Test
    void addOrderRequestWithOneProductsTransactionToAdd() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));

        alpt.add(pt1);

        assertEquals(1, mpl.addOrderRequest(alpt));
    }

    /*
    TC_MPL_3
    -Verify results of method addOrderRequest() with two productsTransaction to add
    */
    @Test
    void addOrderRequestWithTwoProductsTransactionToAdd() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));

        ProductsTransaction pt2 = new ProductsTransaction(mpe1, mpe2, "ID2");
        pt2.addTransactionLine(new ProductTransactionLine("Produto2", 10, 10,
                5, 5));

        alpt.add(pt1);
        alpt.add(pt2);

        assertEquals(2, mpl.addOrderRequest(alpt));
    }

    /*
   TC_MPL_4
   -Verify if OrderRequest quit the OrdersRequestList when registered
   */
    @Test
    void registeredOrderOutOfOrdersRequestList() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        ArrayList<PaymentTransaction> alpayt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));

        PaymentTransaction payt1 = new PaymentTransaction(mpe2, mpe1, "ID1");
        payt1.addTransactionLine(new BasicTransactionLine("EUROS", 101, 1));

        alpt.add(pt1);
        alpayt.add(payt1);

        mpl.addOrderRequest(alpt);
        mpl.registerOrderInLedger(alpayt);

        assertEquals(null, mpl.getOrderRequest("ID1"));
    }

    /*
   TC_MPL_5
   -Verify if an order is registered when the value is not covered
   */
    @Test
    void orderRegisteredWithNoCoveredValue() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        ArrayList<PaymentTransaction> alpayt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));

        PaymentTransaction payt1 = new PaymentTransaction(mpe2, mpe1, "ID1");
        payt1.addTransactionLine(new BasicTransactionLine("EUROS", 10, 1));

        alpt.add(pt1);
        alpayt.add(payt1);

        mpl.addOrderRequest(alpt);

        assertThrows(Exception.class, ()->  mpl.registerOrderInLedger(alpayt).next());
    }

    /*
   TC_MPL_6
   -Verify results registing 0 orders
   */
    @Test
    void orderRegisteredWith0OrdersToRegist() {
        assertThrows(Exception.class, ()->  mpl.registerOrderInLedger(null).next());
    }

    /*
   TC_MPL_7
   -Verify return of method getOrderRequest() with a non existance OrderRequest
   */
    @Test
    void getOrderRequestWithNonExistanceOrderRequest() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));

        alpt.add(pt1);

        mpl.addOrderRequest(alpt);

        assertEquals(null,  mpl.getOrderRequest("ID2"));
    }

    /*
   TC_MPL_8
   -Verify return of method getOrderRequest() with a  existance OrderRequest
   */
    @Test
    void getOrderRequestWithExistanceOrderRequest() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));

        alpt.add(pt1);

        mpl.addOrderRequest(alpt);

        assertEquals(pt1,  mpl.getOrderRequest("ID1"));
    }

    /*
    TC_MPL_9
    -Verify return of method removeOrderRequest() with a non existance OrderRequest
    */
    @Test
    void removeOrderRequestWithNonExistanceOrderRequest() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));

        alpt.add(pt1);

        mpl.addOrderRequest(alpt);

        assertEquals(false,  mpl.removeOrderRequest("ID2"));
    }

    /*
    TC_MPL_10
    -Verify return of method removeOrderRequest() with a removed OrderRequest
    */
    @Test
    void removeOrderRequestWithRemovedOrderRequest() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));

        alpt.add(pt1);
        mpl.addOrderRequest(alpt);
        mpl.removeOrderRequest("ID1");


        assertEquals(false,  mpl.removeOrderRequest("ID1"));
    }

    /*
    TC_MPL_11
    -Verify the return of method getOrdersCounter() with no orders in Ledger
    */
    @Test
    void getOrdersCounterWithNoOrdersInLedger() {
        assertEquals(0, this.mpl.getOrdersCounter());
    }

    /*
    TC_MPL_12
    -Verify the return of method getOrdersCounter() with one order in Ledger
    */
    @Test
    void getOrdersCounterWithOneOrderInLedger() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        ArrayList<PaymentTransaction> alpayt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));

        PaymentTransaction payt1 = new PaymentTransaction(mpe2, mpe1, "ID1");
        payt1.addTransactionLine(new BasicTransactionLine("EUROS", 101, 1));

        alpt.add(pt1);
        alpayt.add(payt1);

        mpl.addOrderRequest(alpt);
        mpl.registerOrderInLedger(alpayt);

        assertEquals(1, mpl.getOrdersCounter());
    }

    /*
    TC_MPL_13
    -Verify the return of method averageTransactionsValue() with empty Ledger
    */
    @Test
    void averageTransactionsValueWithEmptyLedger() {

        assertEquals(0, mpl.averageTransactionsValue());
    }

    /*
    TC_MPL_14
    -Verify the return of method averageTransactionsValue() with two orders in Ledger
    */
    @Test
    void averageTransactionsValueWithTwoOrdersInLedger() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        ArrayList<PaymentTransaction> alpayt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));
        PaymentTransaction payt1 = new PaymentTransaction(mpe2, mpe1, "ID1");
        payt1.addTransactionLine(new BasicTransactionLine("EUROS", 101, 1));

        ProductsTransaction pt2 = new ProductsTransaction(mpe1, mpe2, "ID2");
        pt2.addTransactionLine(new ProductTransactionLine("Produto2", 10, 10,
                5, 5));
        PaymentTransaction payt2 = new PaymentTransaction(mpe2, mpe1, "ID2");
        payt2.addTransactionLine(new BasicTransactionLine("EUROS", 101, 1));

        alpt.add(pt1);
        alpt.add(pt2);
        alpayt.add(payt1);
        alpayt.add(payt2);

        mpl.addOrderRequest(alpt);
        mpl.registerOrderInLedger(alpayt);

        assertEquals(100, mpl.averageTransactionsValue());
    }

    /*
    TC_MPL_15
    -Verify the return of method averageNumberOfProductsPerTransaction() with empty Ledger
    */
    @Test
    void averageNumberOfProductsPerTransactionWith0OrdersInLedger() {
        assertEquals(0, mpl.averageNumberOfProductsPerTransaction());
    }

    /*
    TC_MPL_16
    -Verify the return of method averageNumberOfProductsPerTransaction() with two orders in Ledger
    */
    @Test
    void averageNumberOfProductsPerTransactionWith2OrdersInLedger() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        ArrayList<PaymentTransaction> alpayt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));
        PaymentTransaction payt1 = new PaymentTransaction(mpe2, mpe1, "ID1");
        payt1.addTransactionLine(new BasicTransactionLine("EUROS", 101, 1));

        ProductsTransaction pt2 = new ProductsTransaction(mpe1, mpe2, "ID2");
        pt2.addTransactionLine(new ProductTransactionLine("Produto2", 20, 10,
                5, 5));
        PaymentTransaction payt2 = new PaymentTransaction(mpe2, mpe1, "ID2");
        payt2.addTransactionLine(new BasicTransactionLine("EUROS", 202, 1));

        alpt.add(pt1);
        alpt.add(pt2);
        alpayt.add(payt1);
        alpayt.add(payt2);

        mpl.addOrderRequest(alpt);
        mpl.registerOrderInLedger(alpayt);

        assertEquals(15, mpl.averageNumberOfProductsPerTransaction());
    }

    /*
    TC_MPL_17
    -Verify the return of method averageNumberOfSalesPerDistrict() with empty Ledger
    */
    @Test
    void averageValueOfSalesPerDistrictWith0OrdersInLedger() {
        Map<District, Float> map = mpl.averageValueOfSalesPerDistrict();
        assertEquals(0, map.get(District.BRAGA));
    }

    /*
    TC_MPL_18
    -Verify the return of method averageNumberOfSalesPerDistrict() with 2 orders Ledger
    */
    @Test
    void averageValueOfSalesPerDistrictWith2OrdersInLedger() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        ArrayList<PaymentTransaction> alpayt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));

        PaymentTransaction payt1 = new PaymentTransaction(mpe2, mpe1, "ID1");
        payt1.addTransactionLine(new BasicTransactionLine("EUROS", 101, 1));

        ProductsTransaction pt2 = new ProductsTransaction(mpe1, mpe2, "ID2");
        pt2.addTransactionLine(new ProductTransactionLine("Produto2", 20, 10,
                5, 5));

        PaymentTransaction payt2 = new PaymentTransaction(mpe2, mpe1, "ID2");
        payt2.addTransactionLine(new BasicTransactionLine("EUROS", 202, 1));

        alpt.add(pt1);
        alpt.add(pt2);
        alpayt.add(payt1);
        alpayt.add(payt2);

        mpl.addOrderRequest(alpt);
        mpl.registerOrderInLedger(alpayt);

        Map<District, Float> map = mpl.averageValueOfSalesPerDistrict();

        assertEquals(150, map.get(District.BRAGA));
    }

    /*
    TC_MPL_19
    -Verify the return of method averageNumberOfPurchasesPerDistrict() with empty Ledger
    */
    @Test
    void averageValueOfPurchasesPerDistrictWith0OrdersInLedger() {
        Map<District, Float> map = mpl.averageValueOfSalesPerDistrict();
        assertEquals(0, map.get(District.COIMBRA));
    }

    /*
    TC_MPL_20
    -Verify the return of method averageNumberOfPurchasesPerDistrict() with 2 orders in Ledger
    */
    @Test
    void averageValueOfPurchasesPerDistrictWith2OrdersInLedger() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        ArrayList<PaymentTransaction> alpayt = new ArrayList<>();

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));

        PaymentTransaction payt1 = new PaymentTransaction(mpe2, mpe1, "ID1");
        payt1.addTransactionLine(new BasicTransactionLine("EUROS", 101, 1));

        ProductsTransaction pt2 = new ProductsTransaction(mpe1, mpe2, "ID2");
        pt2.addTransactionLine(new ProductTransactionLine("Produto2", 20, 10,
                5, 5));

        PaymentTransaction payt2 = new PaymentTransaction(mpe2, mpe1, "ID2");
        payt2.addTransactionLine(new BasicTransactionLine("EUROS", 202, 1));

        alpt.add(pt1);
        alpt.add(pt2);
        alpayt.add(payt1);
        alpayt.add(payt2);

        mpl.addOrderRequest(alpt);
        mpl.registerOrderInLedger(alpayt);

        Map<District, Float> map = mpl.averageValueOfPurchasesPerDistrict();

        assertEquals(150, map.get(District.COIMBRA));
    }

    /*
    TC_MPL_21
    -Verify results of method addOrderRequest() with 2 productsTransaction with the same ID
    */
    @Test
    void addOrderRequestWith2ProductsTransactionWithSameID() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID30");
        pt1.addTransactionLine(new ProductTransactionLine("Arroz",10,10,10,10 ));
        ProductsTransaction pt2 = new ProductsTransaction(mpe1, mpe2, "ID30");
        pt2.addTransactionLine(new ProductTransactionLine("Arroz",10,10,10,10 ));
        alpt.add(pt1);
        alpt.add(pt2);
        assertEquals(1, mpl.addOrderRequest(alpt));
    }
}