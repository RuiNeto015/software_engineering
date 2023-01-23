import Transactions.MarketPlaceLedger;
import Transactions.PaymentTransaction;
import Transactions.ProductsTransaction;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        JsonReader jsonReader = new JsonReader("./files/orderRequests.json",
                "./files/payments.json");
        MarketPlaceLedger marketPlaceLedger = new MarketPlaceLedger();
        try {
            ArrayList<ProductsTransaction> orderRequests = jsonReader.getOrderRequestFromJson();
            ArrayList<PaymentTransaction> payments = jsonReader.getPaymentsFromJson();
            marketPlaceLedger.addOrderRequest(orderRequests);
            marketPlaceLedger.registerOrderInLedger(payments);
            marketPlaceLedger.printLedger();
            //marketPlaceLedger.generateExpeditionFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
