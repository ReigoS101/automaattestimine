import Weather.Weather;
import org.junit.BeforeClass;
import org.junit.Test;
import org.json.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.net.URL;
import configurations.Config;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class WeatherTests {

    public static Config config;
    public static String fileName;
    public static String webAPIKeyIs = "f73ff221d406e52eeaa36e28d0e94ecc";
    public static Weather newWeather;
    public static int mock = 2;

    public static void writeLinestoFile(String input) throws IOException {
        Files.write(Paths.get(fileName), input.getBytes(), StandardOpenOption.APPEND);
    }

    private int forecastLength = 3;

    @BeforeClass
    public static void setUpClass() throws IOException {
        config = new Config();
        if (mock == 1) {
            newWeather = Mockito.mock(Weather.class);
        }
        else {
            newWeather = new Weather();
        }
        fileName = "/Users/macbook/Desktop/automaat/src/main/java/textFiles/output.txt";
        writeLinestoFile("\n" + "NEW TEST" + "\n");

    }

    @Test(timeout = 10000)
    public void isUrlOfTodaysWeather() throws IOException {
        if (mock == 1) {
            when(newWeather.makeTodayWeatherRequestUrl(config.getCountyCode(), config.getCityName(), webAPIKeyIs)).thenReturn(new URL("http://api.openweathermap.org/"));
        }

        final URL url = newWeather.makeTodayWeatherRequestUrl(config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        if (mock == 1) verify(newWeather).makeTodayWeatherRequestUrl(config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        //int statusCode = responseStatus;
        assertThat(url, instanceOf(URL.class));

    }

    @Test(timeout = 10000)
    public void isUrlOfWeatherForecast() throws IOException {
        if (mock == 1) {
            when(newWeather.makeWeatherForecastRequestUrl(forecastLength, config.getCountyCode(), config.getCityName(), webAPIKeyIs)).thenReturn(new URL("http://api.openweathermap.org/"));
        }

        final URL url = newWeather.makeWeatherForecastRequestUrl(forecastLength, config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        if (mock == 1) verify(newWeather).makeWeatherForecastRequestUrl(forecastLength, config.getCountyCode(), config.getCityName(), webAPIKeyIs);

        //int statusCode = responseStatus;
        writeLinestoFile(url.toString() + "\n");
        assertThat(url, instanceOf(URL.class));

    }

    @Test(timeout = 10000)
    public void isApiResponse200() throws IOException {
        if (mock == 1) {
            when(newWeather.apiResponseStatus(config.getCountyCode(), config.getCityName(), webAPIKeyIs)).thenReturn(200);
        }

        final Integer responseStatus = newWeather.apiResponseStatus(config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        if (mock == 1) verify(newWeather).apiResponseStatus(config.getCountyCode(), config.getCityName(), webAPIKeyIs);


        Integer statusCode = responseStatus;
        writeLinestoFile(statusCode.toString() + "\n");
        assertEquals(statusCode.intValue(), 200);

    }

    @Test(timeout = 10000)
    public void isResponseOfTodaysWeather() throws IOException, JSONException {
        if (mock == 1) {
            when(newWeather.getTodayCurrentWeather(config.getCountyCode(), config.getCityName(), webAPIKeyIs)).thenReturn(new JSONObject().put("dt", (new Date().getTime() / 1000))
            );
        }

        final JSONObject response = newWeather.getTodayCurrentWeather(config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        if (mock == 1) verify(newWeather).getTodayCurrentWeather(config.getCountyCode(), config.getCityName(), webAPIKeyIs);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Long dateInMilliseconds = response.getLong("dt");
        Date today = new Date();
        Date dateInResponse = new Date(dateInMilliseconds * 1000);
        writeLinestoFile(response.toString() + "\n");
        assertEquals(dateFormat.format(today), dateFormat.format(dateInResponse));
    }

    @Test(timeout = 10000)
    public void isResponseWeatherOfThreeDays() throws IOException, JSONException {
        if (mock == 1) {
            when(newWeather.getThreeDayWeatherForeCast(config.getCountyCode(), config.getCityName(), webAPIKeyIs)).thenReturn(new JSONArray().put(2).put(1).put(3));
        }

        final JSONArray response = newWeather.getThreeDayWeatherForeCast(config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        if (mock == 1) verify(newWeather).getThreeDayWeatherForeCast(config.getCountyCode(), config.getCityName(), webAPIKeyIs);

        int forecastLength = response.length();
        writeLinestoFile(response.toString() + "\n");
        assertEquals(3, forecastLength);

    }


    @Test(timeout = 10000)
    public void isResponseWithCoordinates() throws IOException, JSONException {

        if (mock == 1) {
            when(newWeather.getCoordinatesOfCity(config.getCountyCode(), config.getCityName(), webAPIKeyIs)).thenReturn(new JSONObject().put("lat", 1).put("lon", 2));
        }

        final JSONObject response = newWeather.getCoordinatesOfCity(config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        if (mock == 1) verify(newWeather).getCoordinatesOfCity(config.getCountyCode(), config.getCityName(), webAPIKeyIs);


        Boolean latitude = response.has("lat");
        Boolean longitude = response.has("lon");
        Boolean latAndLon = latitude && longitude;
        writeLinestoFile(response.toString() + "\n");
        assertEquals(true, latAndLon);

    }

    @Test(timeout = 10000)
    public void isResponseOfMaxTemperatures() throws IOException, JSONException {
        if (mock == 1) {
            when(newWeather.getHighestandLowestTempForecast(forecastLength, config.getCountyCode(), config.getCityName(), webAPIKeyIs)).thenReturn(new JSONArray().put(new JSONObject().put("maxTemp", 1).put("minTemp", 2)));
        }

        final JSONArray response = newWeather.getHighestandLowestTempForecast(forecastLength, config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        if (mock == 1) verify(newWeather).getHighestandLowestTempForecast(forecastLength, config.getCountyCode(), config.getCityName(), webAPIKeyIs);


        int temperatureCount = 0;
        for (int i = 0 ; i < response.length(); i++) {
            JSONObject temeperatures = response.getJSONObject(i);
            if (temeperatures.has("maxTemp") && temeperatures.has("minTemp")) {
                temperatureCount++;            }
        }
        writeLinestoFile(response.toString() + "\n");
        assertEquals(response.length(), temperatureCount);
    }

}

