package CostCalculation;

import com.orgcom.District;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BasicShippingFees implements ShippingFees{
    private int NUMBER_OF_DISTRICTS = 18;
    private int[][] distances;
    private BufferedReader distancesBuffer;
    private BufferedReader feesBuffer;
    private float costPerKgKm;
    private float costPerM3;

    public BasicShippingFees(String distancesFilePath, String feesFilePath) {
        this.distances = new int[NUMBER_OF_DISTRICTS][NUMBER_OF_DISTRICTS];
        try {
            this.distancesBuffer = new BufferedReader(new FileReader(distancesFilePath));
            this.feesBuffer = new BufferedReader(new FileReader(feesFilePath));
            this.readDistances();
            this.readFees();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int getIndex(District district) {
        switch (district) {
            case VIANA_CASTELO:
                return 0;
            case BRAGA:
                return 1;
            case VILA_REAL:
                return 2;
            case BRAGANCA:
                return 3;
            case PORTO:
                return 4;
            case VISEU:
                return 5;
            case AVEIRO:
                return 6;
            case GUARDA:
                return 7;
            case COIMBRA:
                return 8;
            case CASTELO_BRANCO:
                return 9;
            case LEIRIA:
                return 10;
            case LISBOA:
                return 11;
            case SANTAREM:
                return 12;
            case PORTALEGRE:
                return 13;
            case SETUBAL:
                return 14;
            case EVORA:
                return 15;
            case BEJA:
                return 16;
            case FARO:
                return 17;
        }
        return -1;
    }

    private void readDistances() {
        String line;
        String splitBy = ";";
        int i = 0;
        try {
            this.distancesBuffer.readLine();
            while ((line = distancesBuffer.readLine()) != null) {
                String[] value = line.split(splitBy);
                for (int k = 1; k < value.length; k++) {
                    this.distances[i][k - 1] = Integer.parseInt(value[k]);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFees() {
        try {
            feesBuffer.readLine();
            costPerKgKm = Float.parseFloat(feesBuffer.readLine());
            feesBuffer.readLine();
            costPerM3 = Float.parseFloat(feesBuffer.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for the shipping fee considering the destination, weight and volume.
     *
     * @param start       the start District
     * @param destination the destination District
     * @param weight      the order weight
     * @param volume      the order volume
     * @return the shipping fee to be applied
     */
    @Override
    public float getShippingFee(District start, District destination, float weight, float volume) {
        if(distances[this.getIndex(start)][this.getIndex(destination)] == 0){
            return volume * this.costPerM3;
        }

        return (weight / distances[this.getIndex(start)][this.getIndex(destination)]) * this.costPerKgKm +
                volume * this.costPerM3;
    }
}
