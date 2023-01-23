package Transactions;

import com.orgcom.District;

import java.util.Map;

public interface Statistics {

    /**
     * Calculates the average transactions value.
     *
     * @return the average transactions value
     */
    float averageTransactionsValue();

    /**
     * Calculates the average number of products per transaction.
     *
     * @return the average number of products per transaction
     */
    float averageNumberOfProductsPerTransaction();

    /**
     * Calculates the average value of sales per district.
     *
     * @return the average value of sales per district
     */
    Map<District, Float> averageValueOfSalesPerDistrict();

    /**
     * Calculates the average value of sales per district.
     *
     * @return the average value of sales per district
     */
    Map<District, Float> averageValueOfPurchasesPerDistrict();
}
