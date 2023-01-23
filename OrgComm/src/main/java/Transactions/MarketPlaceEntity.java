package Transactions;

import com.orgcom.BasicEntity;
import com.orgcom.District;

public class MarketPlaceEntity extends BasicEntity {

    private String address;
    private String vat;

    /**
     * Creates a new Entity with the given name, district, address and vat.
     * The UUID is generated automatically.
     * The tokens are set to 0.
     *
     * @param name     the name of the entity
     * @param district the district of the entity
     * @param address  the address of the entity
     * @param vat      the vat of the entity
     * @throws IllegalArgumentException if the name, address or vat are null or empty, or the district is null
     */
    public MarketPlaceEntity(String name, District district, String address, String vat) {
        super(name, district);

        if(address == null || vat == null){
            throw new IllegalArgumentException("Null arguments are not valid");
        }

        if(vat.length() != 9){
            throw  new IllegalArgumentException("VAT is invalid");
        }

        if(address.isEmpty() || vat.isEmpty()){
            throw new IllegalArgumentException("Empty values are not valid");
        }
        this.address = address;
        this.vat = vat;
    }

    /**
     * Getter for the MarketPlaceEntity address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for the MarketPlaceEntity vat.
     *
     * @return the vat
     */
    public String getVat() {
        return vat;
    }

}
