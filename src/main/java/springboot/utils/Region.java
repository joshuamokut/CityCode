package springboot.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Region {
    private final String regionNumber;

    public Region(@JsonProperty("city_kladr_id") String number){
        this.regionNumber=number;
    }

    public String getRegionNumber() {
        return regionNumber;
    }

}
