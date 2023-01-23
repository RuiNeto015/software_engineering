package CostCalculation;

import com.orgcom.District;

public interface ShippingFees {
    /**
     * Getter for the shipping fee considering the destination, weight and volume.
     *
     * @param start       the start District
     * @param destination the destination District
     * @param weight      the order weight
     * @param volume      the order volume
     * @return the shipping fee to be applied
     */
    float getShippingFee(District start, District destination, float weight, float volume);
}
