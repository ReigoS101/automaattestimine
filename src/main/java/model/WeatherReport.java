package model;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherReport {

    public String getCityName() {
        return cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Double getCurrentTemp() {
        return currentTemp;
    }

    public JSONArray getThreeDayTemp() {
        return threeDayTemp;
    }

    public JSONObject getGeoCoordinates() {
        return geoCoordinates;
    }

    public Double getLongitude() {
        return this.getGeoCoordinates().getDouble("lon");
    }

    public Double getLatitude() {
        return this.getGeoCoordinates().getDouble("lat");
    }

    public final String cityName;
    public final String countryCode;
    public final JSONObject geoCoordinates;
    public final Double currentTemp;
    public final JSONArray threeDayTemp;

    @Override
    public String toString() {
        return "WeatherReport{ \n" +
                "cityName='" + cityName + '\'' + "\n" +
                ", countryCode='" + countryCode + '\'' +"\n" +
                ", geoCoordinates=" + geoCoordinates + "\n" +
                ", currentTemp='" + currentTemp + '\'' + "\n" +
                ", threeDayTemp=" + threeDayTemp + "\n" +
                '}' + "\n";
    }

    public WeatherReport(String cityName, String countryCode, JSONObject geoCoordinates, Double currentTemp, JSONArray threeDayTemp) {
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.geoCoordinates = geoCoordinates;
        this.currentTemp = currentTemp;
        this.threeDayTemp = threeDayTemp;
    }
}
