package org.tests;

import org.apiManager.OpenWeatherMapApiFunctions;
import org.apiManager.OpenWeatherMapEndPoints;
import org.enums.ServiceName;
import org.factory.ApiClientFactory;
import org.responses.GetWeatherResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import static java.net.HttpURLConnection.HTTP_OK;

public class OpenWeatherMapTests {
    private final OpenWeatherMapApiFunctions openWeatherMapApiFunctions = new OpenWeatherMapApiFunctions();
    private final ApiClientFactory client = new ApiClientFactory();
    private OpenWeatherMapEndPoints openWeatherMapEndPoints;

    private final String API_KEY = "a6919dd2e20c5ca67bcf1c727a0b36bf";
    private final String EXPECTED_UK_PREFIX = "GB";
    private final String EXPECTED_IL_PREFIX = "IL";

    @BeforeClass
    private void init() throws Exception {
        openWeatherMapEndPoints = client.ClientBuilder(ServiceName.OPEN_WEATHER_MAP.getServiceName(), OpenWeatherMapEndPoints.class);
    }

    @Test
    public void test_getWeathers() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            GetWeatherResponse getWeatherResponse;
            try {
                getWeatherResponse = openWeatherMapApiFunctions.getWeather(openWeatherMapEndPoints, "London,uk", API_KEY, "imperial");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            verify(getWeatherResponse, EXPECTED_UK_PREFIX);
            print_temperature(getWeatherResponse);
        });

        Thread thread2 = new Thread(() -> {
            GetWeatherResponse getWeatherResponse;
            try {
                getWeatherResponse = openWeatherMapApiFunctions.getWeather(openWeatherMapEndPoints, "Tel-Aviv,IL", API_KEY, "metric");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            verify(getWeatherResponse, EXPECTED_IL_PREFIX);
            print_temperature(getWeatherResponse);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    private void verify(GetWeatherResponse getWeatherResponse, String expectedPrefixCountry) {
        assert getWeatherResponse.getCod() == HTTP_OK;
        assert getWeatherResponse.getSys().getCountry().equals(expectedPrefixCountry);
        assert getWeatherResponse.getMain().getTemp() > 0;
    }

    private void print_temperature(GetWeatherResponse getWeatherResponse) {
        String output = (getWeatherResponse.getSys().getCountry().equals(EXPECTED_UK_PREFIX))
                ? String.format("The weather in GB in Fahrenheit is: %.2f°F", getWeatherResponse.getMain().getTemp())
                : (getWeatherResponse.getSys().getCountry().equals(EXPECTED_IL_PREFIX))
                ? String.format("The weather in IL in Celsius is: %.2f°C", getWeatherResponse.getMain().getTemp())
                : "Unknown country";

        System.out.println(output);
    }
}
