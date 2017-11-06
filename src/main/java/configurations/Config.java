package configurations;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Config {

    private String countyCode;
    private String cityName;
    private String key;


    public Config() {
        readInputFile();
        try {
            List configData = readInputFile();
            this.cityName = configData.get(0).toString().trim();
            this.countyCode = configData.get(1).toString().trim();
            this.key = configData.get(2).toString().trim();
        } catch (Exception e) {
            System.out.println("Error!" + e.getMessage());
        }
    }

    public static List readInputFile() {
        List<String> inputLineList = new ArrayList<>();
        List<String> newConfigValues = new ArrayList<>();

        String fileName = "/Users/macbook/IdeaProjects/AutomaatTestimine/src/main/java/textFiles/input.txt";

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            //br returns as stream and convert it into a List
            inputLineList = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputLineList.forEach(line -> {
            String [] split = line.split(",");
            for (int i = 0; i < split.length; i++) {
                newConfigValues.add(String.valueOf(split[i]));
            }
        });

        return newConfigValues;
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
