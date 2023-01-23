import Transactions.MarketPlaceEntity;
import Transactions.PaymentTransaction;
import Transactions.ProductTransactionLine;
import Transactions.ProductsTransaction;
import com.orgcom.BasicTransactionLine;
import com.orgcom.District;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class JsonReader {

    private String orderRequestsFilePath;
    private String ordersPaymentsFilePath;

    public JsonReader(String orderRequestsFilePath, String ordersPaymentsFilePath) {
        this.orderRequestsFilePath = orderRequestsFilePath;
        this.ordersPaymentsFilePath = ordersPaymentsFilePath;
    }

    private MarketPlaceEntity getMarketPlaceEntity(JSONObject orderRequest, String type){
        JSONObject jEntity = (JSONObject) orderRequest.get(type);
        return new MarketPlaceEntity(jEntity.get("name").toString(),
                District.valueOf(jEntity.get("district").toString().toUpperCase()), jEntity.get("address")
                .toString(), jEntity.get("vat").toString());
    }

    private void getTransactionLines(JSONObject jOrderRequest, ProductsTransaction productsTransaction){
        JSONArray jProductTransactionLines = (JSONArray) jOrderRequest.get("products");
        Iterator transactionLinesIt = jProductTransactionLines.iterator();
        while(transactionLinesIt.hasNext()){
            JSONObject tl = (JSONObject) transactionLinesIt.next();
            productsTransaction.addTransactionLine(new ProductTransactionLine(tl.get("description").toString(),
                    Integer.parseInt(tl.get("quantity").toString()), Double.parseDouble(tl.get("price").toString()),
                    Float.parseFloat(tl.get("volume-m3").toString()), Float.parseFloat(tl.get("weight-kg").toString())));
        }
    }

    public ArrayList<ProductsTransaction> getOrderRequestFromJson() throws IOException, ParseException {
        ArrayList<ProductsTransaction> orderRequests = new ArrayList<>();
        Object obj = new JSONParser().parse(new FileReader(this.orderRequestsFilePath));
        JSONObject jo = (JSONObject) obj;
        JSONArray ja = (JSONArray) jo.get("orders");
        Iterator orderRequestsIt = ja.iterator();

        while (orderRequestsIt.hasNext()) {
            JSONObject jOrderRequest = (JSONObject) orderRequestsIt.next();
            String orderID = (String) jOrderRequest.get("id");
            MarketPlaceEntity receiver = this.getMarketPlaceEntity(jOrderRequest, "receiver");
            MarketPlaceEntity sender = this.getMarketPlaceEntity(jOrderRequest, "sender");
            ProductsTransaction productsTransaction = new ProductsTransaction(sender, receiver, orderID);
            this.getTransactionLines(jOrderRequest, productsTransaction);
            orderRequests.add(productsTransaction);
        }
        return orderRequests;
    }

    public ArrayList<PaymentTransaction> getPaymentsFromJson() throws IOException, ParseException {
        ArrayList<PaymentTransaction> payments = new ArrayList<>();
        Object obj = new JSONParser().parse(new FileReader(this.ordersPaymentsFilePath));
        JSONObject jo = (JSONObject) obj;
        JSONArray ja = (JSONArray) jo.get("payments");
        Iterator paymentsIt = ja.iterator();

        while (paymentsIt.hasNext()) {
            JSONObject jPayment = (JSONObject) paymentsIt.next();
            String orderID = (String) jPayment.get("id");
            MarketPlaceEntity receiver = this.getMarketPlaceEntity(jPayment, "receiver");
            MarketPlaceEntity sender = this.getMarketPlaceEntity(jPayment, "sender");
            PaymentTransaction paymentTransaction = new PaymentTransaction(sender, receiver, orderID);
            JSONObject jPaymentTl = (JSONObject) jPayment.get("payment");
            paymentTransaction.addTransactionLine(new BasicTransactionLine(jPaymentTl.get("currency").toString(),
                    Integer.parseInt(jPaymentTl.get("quantity").toString()), Double.parseDouble(jPaymentTl
                    .get("unitPrice").toString())));
            payments.add(paymentTransaction);
        }
        return payments;
    }
}
