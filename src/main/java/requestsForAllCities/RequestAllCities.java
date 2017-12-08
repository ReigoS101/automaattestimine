package requestsForAllCities;


import Weather.Weather;
import fileReaderWriter.fileReaderWriter;
import model.WeatherReport;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class RequestAllCities {

    public static void requestWeatherInfoForAll(String filePath) {
        Weather weather = new Weather();
        JSONArray requestInfo = fileReaderWriter.readAllLines(filePath);
        for(int i = 0; i < requestInfo.length(); i++) {
            String [] split = requestInfo.get(i).toString().split(",");
            String cityName = split[0].trim();
            String countyCode = split[1].trim();
            String key = split[2].trim();
            try {
                JSONObject currentWeatherResponse = weather.getTodayCurrentWeather(countyCode, cityName, key);
                JSONArray foreCastResponse = weather.getThreeDayWeatherForeCast(countyCode, cityName, key);
                System.out.println(foreCastResponse);
                try {
                    String fileName = "/Users/macbook/Desktop/automaat/src/main/java/textFiles/cities/" + cityName +".txt"; //Muuda ära
                    File file = new File(fileName);
                    file.createNewFile();
                    Double currentTemp = currentWeatherResponse.getJSONObject("main").getDouble("temp");
                    JSONObject coords = currentWeatherResponse.getJSONObject("coord");
                    JSONArray threeDayWeatherArray = foreCastResponse;
                    JSONArray forecastArray = new JSONArray();
                    JSONObject forecast = new JSONObject();
                    for(int e = 0; e < threeDayWeatherArray.length(); e++) {
                        long dateUnix = threeDayWeatherArray.getJSONObject(e).getLong("dt");
                        Double max_temp = threeDayWeatherArray.getJSONObject(e).getJSONObject("main").getDouble("temp_max");
                        Double min_temp = threeDayWeatherArray.getJSONObject(e).getJSONObject("main").getDouble("temp_min");
                        forecast.put("max_temp", max_temp);
                        forecast.put("min_temp", min_temp);
                        forecast.put("dateUnix", dateUnix);
                        forecastArray.put(e, forecast);
                    }

                    fileReaderWriter.addStringToFile(new WeatherReport(cityName, countyCode, coords, currentTemp, forecastArray).toString() + " \n", fileName);
                    //fileReaderWriter.addStringToFile(foreCastReport.toString() + " \n", fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        RequestAllCities.requestWeatherInfoForAll("/Users/macbook/Desktop/automaat/src/main/java/textFiles/input.txt");
        System.exit(0);
    }
}
