package org.apiManager;

import okhttp3.ResponseBody;
import org.apiHelper.ResponseValidations;
import retrofit2.Call;
import retrofit2.Response;
import java.io.IOException;

public class OpenWeatherMapApiValidate {
    private final ResponseValidations validations = new ResponseValidations();

    public Response<ResponseBody> getWeather(OpenWeatherMapEndPoints openWeatherMapEndPoints, String location, String apiKey, String unit) throws IOException {
        Call<ResponseBody> call = openWeatherMapEndPoints.getWeather(location, apiKey, unit);
        printRequest(call);
        return validations.ok(call);
    }

    private static void printRequest(Call<ResponseBody> call) {
        System.out.println("API REQUEST: [" + call.request().method() + "] " + call.request().url());
    }
}
