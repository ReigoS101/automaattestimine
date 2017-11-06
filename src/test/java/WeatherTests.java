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


public class WeatherTests {

    public static Config config;
    public static String fileName;
    public static String webAPIKeyIs = "f73ff221d406e52eeaa36e28d0e94ecc";

    public static void writeLinestoFile(String input) throws IOException {
        Files.write(Paths.get(fileName), input.getBytes(), StandardOpenOption.APPEND);
    }

    Weather newWeather = new Weather();
    private int forecastLength = 3;

    @BeforeClass
    public static void setUpClass() throws IOException {
        config = new Config();
        fileName = "/Users/macbook/IdeaProjects/AutomaatTestimine/src/main/java/textFiles/output.txt";
        writeLinestoFile("\n" + "NEW TEST" + "\n");

    }

    @Test(timeout = 10000)
    public void isUrlOfTodaysWeather() throws IOException {
        final URL url = newWeather.makeTodayWeatherRequestUrl(config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        //int statusCode = responseStatus;
        assertThat(url, instanceOf(URL.class));

    }

    @Test(timeout = 10000)
    public void isUrlOfWeatherForecast() throws IOException {
        final URL url = newWeather.makeWeatherForecastRequestUrl(forecastLength, config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        //int statusCode = responseStatus;
        writeLinestoFile(url.toString() + "\n");
        assertThat(url, instanceOf(URL.class));

    }

    @Test(timeout = 10000)
    public void isApiResponse200() throws IOException {
        final Integer responseStatus = newWeather.apiResponseStatus(config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        Integer statusCode = responseStatus;
        writeLinestoFile(statusCode.toString() + "\n");
        assertEquals(statusCode.intValue(), 200);

    }

    @Test(timeout = 10000)
    public void isResponseOfTodaysWeather() throws IOException, JSONException {
        final JSONObject response = newWeather.getTodayCurrentWeather(config.getCountyCode(), config.getCityName(), webAPIKeyIs);
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
        final JSONArray response = newWeather.getThreeDayWeatherForeCast(config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        int forecastLength = response.length();
        writeLinestoFile(response.toString() + "\n");
        assertEquals(3, forecastLength);

    }


    @Test(timeout = 10000)
    public void isResponseWithCoordinates() throws IOException, JSONException {
        final JSONObject response = newWeather.getCoordinatesOfCity(config.getCountyCode(), config.getCityName(), webAPIKeyIs);
        Boolean latitude = response.has("lat");
        Boolean longitude = response.has("lon");
        Boolean latAndLon = latitude && longitude;
        writeLinestoFile(response.toString() + "\n");
        assertEquals(true, latAndLon);

    }

    @Test(timeout = 10000)
    public void isResponseOfMaxTemperatures() throws IOException, JSONException {
        final JSONArray response = newWeather.getHighestandLowestTempForecast(forecastLength, config.getCountyCode(), config.getCityName(), webAPIKeyIs);
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

