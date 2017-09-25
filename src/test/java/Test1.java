import org.junit.Test;

import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class Test1 {

    @Test
    public void isHttpResponse200() throws IOException{
        assertEquals(Weather.httpResponseStatusNumber, 200);

    }

    @Test
    public void doesURLstartWithOpenWeatherMap() throws IOException{
        assertEquals(Weather.webURLStartsWith, "http://openweathermap.org/data/2.5/");
    }
    @Test
    public void isAPIKeyCorrect() throws IOException{
        assertEquals(Weather.webAPIKeyIs, "f73ff221d406e52eeaa36e28d0e94ecc");
    }

    @Test
    public void isTodaysForecast() throws IOException{

        assertEquals(Weather.webApiResponseTodaysDate, "2017-09-24");
    }

    @Test
    public void forecastHasLatAndLon() throws IOException {
        Boolean responseLat = Weather.webApiResponseHasLat.equals("lat");
        Boolean responseLon = Weather.webApiResponseHasLon.equals("lon");
        Boolean responseLatAndLon = responseLat & responseLon;
        assertEquals(true, responseLatAndLon);

    }
}
