package org.apiManager;

import okhttp3.ResponseBody;
import org.apiHelper.Deserialization;
import org.responses.GetWeatherResponse;
import retrofit2.Response;
import java.io.IOException;

public class OpenWeatherMapApiFunctions {
    private final OpenWeatherMapApiValidate openWeatherMapApiValidate = new OpenWeatherMapApiValidate();
    private final Deserialization deserialization = new Deserialization();

    public GetWeatherResponse getWeather(OpenWeatherMapEndPoints openWeatherMapEndPoints, String location, String apiKey, String unit) throws IOException {
        Response<ResponseBody> okResponse =  openWeatherMapApiValidate.getWeather(openWeatherMapEndPoints, location, apiKey, unit);
        return deserialization.getObject(okResponse, GetWeatherResponse.class);
    }
}
