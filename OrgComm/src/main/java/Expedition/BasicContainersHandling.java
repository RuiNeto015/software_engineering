package Expedition;

import Transactions.ProductsTransaction;
import com.orgcom.BasicBlock;
import com.orgcom.Block;
import com.orgcom.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class BasicContainersHandling {
    private ArrayList<BasicContainer> containers;
    private FileWriter writer;

    /**
     * ContainersHandling class constructor.
     *
     * @param filepath the filepath for the json destination
     */
    public BasicContainersHandling(String filepath) {
        this.containers = new ArrayList<>();
        try {
            this.writer = new FileWriter(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a Container.
     *
     */
    public void addContainer(BasicContainer container) {
        this.containers.add(container);
    }

    private void writeToJson(String content) {
        try {
            this.writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a json file with all the containers info.
     *
     */
    public void exportToJson() {
        writeToJson("{\n");
        writeToJson("\"containers\": [\n");
        for (int i = 0; i < this.containers.size(); i++) {
            if (i != 0) {
                writeToJson(",");
            }
            writeToJson("{\"container\": {\n");
            writeToJson("\"district\": \"" + this.containers.get(i).getDistrict() + "\",\n");
            writeToJson("\"usedSpace\": " + this.containers.get(i).getUsedSpace() + ",\n");
            writeToJson(" \"orders\": [\n");

            Iterator<Block> it = this.containers.get(i).getOrders();
            boolean flag = true;
            while (it.hasNext()) {
                BasicBlock basicBlock = (BasicBlock) it.next();
                Iterator<Transaction> transactionIterator = basicBlock.getTransactions();
                ProductsTransaction productsTransaction = (ProductsTransaction) transactionIterator.next();
                if (flag != true) {
                    writeToJson(",");
                }
                flag = false;
                writeToJson("{ \"order\": {\n");
                writeToJson("\"orderId\": \"" + productsTransaction.getOrderId() + "\",\n");
                writeToJson("\"hash\": \"" + productsTransaction.getHash() + "\",\n");
                writeToJson("\"receiver\": \"" + productsTransaction.getReceiver().getName() + "\"," +
                        "\n");
                writeToJson("\"orderVolume\": \"" + productsTransaction.getVolume() + "\",\n");
                writeToJson("\"weight\": \"" + productsTransaction.getWeight() + "\"\n");
                writeToJson("}}\n");
            }
            writeToJson("]\n");
            writeToJson("}}\n");
        }
        writeToJson("]\n");
        writeToJson("}");
        try {
            this.writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
