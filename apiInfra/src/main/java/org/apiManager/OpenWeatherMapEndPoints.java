package org.apiManager;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.http.*;

public interface OpenWeatherMapEndPoints {

    @GET("data/2.5/weather")
    Call<ResponseBody> getWeather(@Query("q") String location, @Query("APPID") String apiKey, @Query("units") @Nullable String unit);
}
