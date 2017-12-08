package configurations;

import java.util.List;
import fileReaderWriter.fileReaderWriter;


public class Config {

    private String countyCode;
    private String cityName;
    private String key;


    public Config() {
        try {
            List configData =  fileReaderWriter.readLinesSpecific("/Users/macbook/Desktop/automaat/src/main/java/textFiles/input.txt");
            this.cityName = configData.get(0).toString().trim();
            this.countyCode = configData.get(1).toString().trim();
            this.key = configData.get(2).toString().trim();
        } catch (Exception e) {
            System.out.println("Error!" + e.getMessage());
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) { this.key = key; }


    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
