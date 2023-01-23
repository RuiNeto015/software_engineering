package Transactions;

import com.orgcom.BasicTransactionLine;

public class ProductTransactionLine extends BasicTransactionLine {

    private float volume;
    private float weight;

    /**
     * Creates a new ProductTransactionLine line.
     *
     * @param itemDescription Item in the transaction line.
     * @param quantity        Quantity of the item in transaction line.
     * @param unitPrice       Unit Price of the item in the transaction line.
     * @param volume          Unit's volume in m3.
     * @param weight          Unit's weight in Kg.
     * @throws IllegalArgumentException If any of the arguments are invalid (item description is null or empty,
     * quantity, volume or weight is less or equal than 0 and unitPrice is negative).
     */
    public ProductTransactionLine(String itemDescription, int quantity, double unitPrice, float volume, float weight) {
        super(itemDescription, quantity, unitPrice);

        if(volume <= 0 || weight <= 0) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        this.volume = volume;
        this.weight = weight;
    }

    /**
     * Getter for the product's total volume.
     *
     * @return the product's total volume.
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Getter for the product's total weight.
     *
     * @return the product's total weight.
     */
    public float getWeight() {
        return weight;
    }
}
