import org.junit.Test;
import org.json.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.net.URL;

public class WeatherTests {


    public static String countryCode = "EE";
    public static String cityName = "Tallinn";
    public static String webAPIKeyIs = "f73ff221d406e52eeaa36e28d0e94ecc";

    Weather newWeather = new Weather();
    private int forecastLength = 3;

    @Test(timeout = 10000)
    public void isUrlOfTodaysWeather() throws IOException {
        final URL url = newWeather.makeTodayWeatherRequestUrl(countryCode, cityName, webAPIKeyIs);
        //int statusCode = responseStatus;
        assertThat(url, instanceOf(URL.class));

    }

    @Test(timeout = 10000)
    public void isUrlOfWeatherForecast() throws IOException {
        final URL url = newWeather.makeWeatherForecastRequestUrl(forecastLength, countryCode, cityName, webAPIKeyIs);
        //int statusCode = responseStatus;
        assertThat(url, instanceOf(URL.class));

    }

    @Test(timeout = 10000)
    public void isApiResponse200() throws IOException {
        final Integer responseStatus = newWeather.apiResponseStatus(countryCode, cityName, webAPIKeyIs);
        int statusCode = responseStatus;
        assertEquals(statusCode, 200);

    }

    @Test(timeout = 10000)
    public void isResponseOfTodaysWeather() throws IOException, JSONException {
        final JSONObject response = newWeather.getTodayCurrentWeather(countryCode, cityName, webAPIKeyIs);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Long dateInMilliseconds = response.getLong("dt");
        Date today = new Date();
        Date dateInResponse = new Date(dateInMilliseconds * 1000);
        assertEquals(dateFormat.format(today), dateFormat.format(dateInResponse));
    }

    @Test(timeout = 10000)
    public void isResponseWeatherOfThreeDays() throws IOException, JSONException {
        final JSONArray response = newWeather.getThreeDayWeatherForeCast(countryCode, cityName, webAPIKeyIs);
        int forecastLength = response.length();
        assertEquals(3, forecastLength);

    }


    @Test(timeout = 10000)
    public void isResponseWithCoordinates() throws IOException, JSONException {
        final JSONObject response = newWeather.getCoordinatesOfCity(countryCode, cityName, webAPIKeyIs);
        Boolean latitude = response.has("lat");
        Boolean longitude = response.has("lon");
        Boolean latAndLon = latitude && longitude;
        assertEquals(true, latAndLon);

    }

    @Test(timeout = 10000)
    public void isResponseOfMaxTemperatures() throws IOException, JSONException {
        final JSONArray response = newWeather.getHighestandLowestTempForecast(forecastLength, countryCode, cityName, webAPIKeyIs);
        int temperatureCount = 0;
        for (int i = 0 ; i < response.length(); i++) {
            JSONObject temeperatures = response.getJSONObject(i);
            if (temeperatures.has("maxTemp") && temeperatures.has("minTemp")) {
                temperatureCount++;            }
        }
        assertEquals(response.length(), temperatureCount);
    }

}

