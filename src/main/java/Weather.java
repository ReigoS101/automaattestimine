import java.io.IOException;
import java.net.URL;
import okhttp3.OkHttpClient;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.Request;
import org.json.*;



public class Weather {

  OkHttpClient client = new OkHttpClient();

    public URL makeTodayWeatherRequestUrl(String countryCode, String cityName, String webAPIKeyIs) {
        URL currentWeather = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/weather")
                .addQueryParameter("q", countryCode+","+cityName)
                .addQueryParameter("APPID", webAPIKeyIs)
                .build().url();
        return currentWeather;
    }

    public URL makeWeatherForecastRequestUrl(Integer foreCastLengthInDays, String countryCode, String cityName, String webAPIKeyIs) {
        URL weatherForecast = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/forecast")
                .addQueryParameter("q", countryCode+","+cityName)
                .addQueryParameter("APPID", webAPIKeyIs)
                .addQueryParameter("cnt", String.valueOf(foreCastLengthInDays))
                .build().url();
        return weatherForecast;
    }


    private String makeAPIGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private Integer getAPIResponseCode(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.code();
    }

    public Integer apiResponseStatus(String countryCode, String cityName, String APIKey) throws IOException {
        final Integer apiResponseStatus = getAPIResponseCode(makeTodayWeatherRequestUrl(countryCode, cityName, APIKey).toString());
        return apiResponseStatus;
    }

    public JSONObject getTodayCurrentWeather(String countryCode, String cityName, String webAPIKeyIs) throws IOException, JSONException {
        final String response = makeAPIGetRequest(makeTodayWeatherRequestUrl(countryCode, cityName, webAPIKeyIs).toString());
        JSONObject jsonResp = new JSONObject(response.toString());
        return jsonResp;
    }

    public JSONArray getWeatherForecast(Integer foreCastLength, String countryCode, String cityName, String APIKey) throws IOException, JSONException {
        final String response = makeAPIGetRequest(makeWeatherForecastRequestUrl(foreCastLength, countryCode, cityName, APIKey).toString());
        JSONObject jsonResp = new JSONObject(response.toString());
        JSONArray weatherForecast = jsonResp.getJSONArray("list");
        return weatherForecast;
    }

    public JSONArray getThreeDayWeatherForeCast(String countryCode, String cityName, String APIKey) throws IOException, JSONException {
        final String response = makeAPIGetRequest(makeWeatherForecastRequestUrl(3, countryCode, cityName, APIKey).toString());
        JSONObject jsonResp = new JSONObject(response.toString());
        JSONArray weatherForecast = jsonResp.getJSONArray("list");
        return weatherForecast;
    }

    public JSONObject getCoordinatesOfCity(String countryCode, String cityName, String APIKey) throws IOException, JSONException {
        final String response = makeAPIGetRequest(makeTodayWeatherRequestUrl(countryCode, cityName, APIKey).toString());
        JSONObject jsonResp = new JSONObject(response.toString());
        JSONObject coordinates = jsonResp.getJSONObject("coord");
        return coordinates;
    }

    public JSONArray getHighestandLowestTempForecast(Integer foreCastLength, String countryCode, String cityName, String APIKey) throws IOException, JSONException {
        final String response = makeAPIGetRequest(makeWeatherForecastRequestUrl(foreCastLength, countryCode, cityName, APIKey).toString());
        JSONObject jsonResp = new JSONObject(response.toString());
        JSONArray weatherInfo = jsonResp.getJSONArray("list");
        JSONArray maxTemperatures = new JSONArray();
        for (int i = 0 ; i < weatherInfo.length(); i++) {
            JSONObject weather = weatherInfo.getJSONObject(i);
            JSONObject dayInfo = new JSONObject();
            Long date = weather.getLong("dt");
            Double highestTemp = weather.getJSONObject("main").getDouble("temp_max");
            Double lowestTemp = weather.getJSONObject("main").getDouble("temp_min");
            dayInfo.put("maxTemp", new Double(highestTemp));
            dayInfo.put("minTemp", new Double(lowestTemp));
            dayInfo.put("date", new Long(date));
            maxTemperatures.put(dayInfo);
        }
        return maxTemperatures;
    }



}
