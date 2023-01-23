package Expedition;

import Transactions.*;
import com.orgcom.BasicTransactionLine;
import com.orgcom.Block;
import com.orgcom.District;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class BasicContainerTest {

    MarketPlaceEntity mpe1, mpe2;
    MarketPlaceLedger mpl;

    @BeforeEach
    public void setup() {
        mpe1 = new MarketPlaceEntity("Entity 1", District.BRAGA,
                "Rua das Pombinhas", "123456789");
        mpe2 = new MarketPlaceEntity("Entity 2", District.COIMBRA,
                "Portela Grande", "987654321");
        mpl = new MarketPlaceLedger();
    }

    /*
    TC_BC_1
    -Verify return of method getUsedSpace() with empty container
    */
    @Test
    void getUsedSpaceWithEmptyContainer() {
        BasicContainer bc = new BasicContainer(District.BRAGA);
        assertEquals(0, bc.getUsedSpace());
    }

    /*
    TC_BC_2
    -Verify return of method getUsedSpace() with 2 orders in the container
    */
    @Test
    void getUsedSpaceWith2OrdersInTheContainer() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        ArrayList<PaymentTransaction> alpayt = new ArrayList<>();
        BasicContainer bc = new BasicContainer(District.BRAGA);

        ProductsTransaction pt1 = new ProductsTransaction(mpe2, mpe1, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));
        PaymentTransaction payt1 = new PaymentTransaction(mpe1, mpe2, "ID1");
        payt1.addTransactionLine(new BasicTransactionLine("EUROS", 101, 1));

        ProductsTransaction pt2 = new ProductsTransaction(mpe2, mpe1, "ID2");
        pt2.addTransactionLine(new ProductTransactionLine("Produto2", 10, 10,
                5, 5));
        PaymentTransaction payt2 = new PaymentTransaction(mpe1, mpe2, "ID2");
        payt2.addTransactionLine(new BasicTransactionLine("EUROS", 101, 1));

        alpt.add(pt1);
        alpt.add(pt2);
        alpayt.add(payt1);
        alpayt.add(payt2);

        mpl.addOrderRequest(alpt);
        mpl.registerOrderInLedger(alpayt);

        Iterator<Block> mpliterator = mpl.ledgerIterator();
        while(mpliterator.hasNext()){
            Block block = mpliterator.next();
            bc.addOrder(block);
        }

        assertEquals(10, bc.getUsedSpace());
    }

    /*
    TC_BC_3
    -Verify return of method getDistrict()
    */
    @Test
    void getContainerDistrict() {
        BasicContainer bc = new BasicContainer(District.BRAGA);
        assertEquals(District.BRAGA, bc.getDistrict());
    }

    /*
    TC_BC_4
    -Verify return of method addOrder() adding an order with different destiny district
    */
    @Test
    void addOrderWithDifferentDestinyDistrict() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        ArrayList<PaymentTransaction> alpayt = new ArrayList<>();
        BasicContainer bc = new BasicContainer(District.BRAGA);

        ProductsTransaction pt1 = new ProductsTransaction(mpe1, mpe2, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                5, 5));
        PaymentTransaction payt1 = new PaymentTransaction(mpe2, mpe1, "ID1");
        payt1.addTransactionLine(new BasicTransactionLine("EUROS", 101, 1));

        alpt.add(pt1);
        alpayt.add(payt1);

        mpl.addOrderRequest(alpt);
        mpl.registerOrderInLedger(alpayt);

        Iterator<Block> mpliterator = mpl.ledgerIterator();

        Block block = mpliterator.next();

        assertEquals(0, bc.addOrder(block));
    }

    /*
    TC_BC_5
    -Verify return of method addOrder() adding an order with bigger volume than the container capacity
    */
    @Test
    void addOrderWithBiggerVolumeThanContainerCapacity() {
        ArrayList<ProductsTransaction> alpt = new ArrayList<>();
        ArrayList<PaymentTransaction> alpayt = new ArrayList<>();
        BasicContainer bc = new BasicContainer(District.BRAGA);

        ProductsTransaction pt1 = new ProductsTransaction(mpe2, mpe1, "ID1");
        pt1.addTransactionLine(new ProductTransactionLine("Produto1", 10, 10,
                64, 5));
        PaymentTransaction payt1 = new PaymentTransaction(mpe1, mpe2, "ID1");
        payt1.addTransactionLine(new BasicTransactionLine("EUROS", 150, 1));

        alpt.add(pt1);
        alpayt.add(payt1);

        mpl.addOrderRequest(alpt);
        mpl.registerOrderInLedger(alpayt);

        Iterator<Block> mpliterator = mpl.ledgerIterator();

        Block block = mpliterator.next();

        assertEquals(-1, bc.addOrder(block));
    }

    /*
    TC_BC_6
    -Verify return of method getOrders() with empty container
    */
    @Test
    void getOrdersWithEmptyContainer() {
        BasicContainer bc = new BasicContainer(District.BRAGA);
        Iterator<Block> it = bc.getOrders();
        assertThrows(Exception.class, ()-> it.next());
    }
}