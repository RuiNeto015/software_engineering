import Transactions.*;
import com.orgcom.*;

import java.util.ArrayList;

public class Main {
    public static void main(String args[]){
        MarketPlaceLedger marketPlaceLedger = new MarketPlaceLedger();
        MarketPlaceEntity entity1 = new MarketPlaceEntity("A", District.BEJA, "ruaXpto", "123456789");
        MarketPlaceEntity entity2 = new MarketPlaceEntity("B", District.BEJA, "ruaXpto", "123456789");

        ProductsTransaction p1 =  new ProductsTransaction(entity1, entity2, "AAAA1");
        p1.addTransactionLine(new ProductTransactionLine("Cadeira", 20, 20, 50, 100));

        ProductsTransaction p2 =  new ProductsTransaction(entity2, entity1, "AAAA2");
        p2.addTransactionLine(new ProductTransactionLine("Mesa", 2, 20, 5, 100));
        p2.addTransactionLine(new ProductTransactionLine("Porta", 2, 20, 5, 100));

        PaymentTransaction pay1 = new PaymentTransaction(entity2, entity1, "AAAA1");
        PaymentTransaction pay2 = new PaymentTransaction(entity1, entity2, "AAAA2");

        TransactionLine transactionLine1 =  new BasicTransactionLine("$", 1000, 1);
        pay1.addTransactionLine(transactionLine1);

        TransactionLine transactionLine2 =  new BasicTransactionLine("$", 111, 1);
        pay2.addTransactionLine(transactionLine2);

        ArrayList<ProductsTransaction> transactionArrayList = new ArrayList<>();
        transactionArrayList.add(p1);
        transactionArrayList.add(p2);

        ArrayList<PaymentTransaction> paymentTransactions = new ArrayList<>();
        paymentTransactions.add(pay1);
        paymentTransactions.add(pay2);

        marketPlaceLedger.addOrderRequest(transactionArrayList);
        marketPlaceLedger.registerOrderInLedger(paymentTransactions);
        marketPlaceLedger.printLedger();
        marketPlaceLedger.generateExpeditionFile();
    }
}
