package org.factory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApiClientFactory {

    public <T> T ClientBuilder(String specificKey, Class<T> clientClass) throws Exception {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(60, TimeUnit.SECONDS);
        client.readTimeout(60, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(client.build())
                .baseUrl(getBaseUrl(specificKey))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clientClass);
    }

    private static String getBaseUrl(String specificKey) throws Exception {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("../envParams.properties");
        properties.load(fileInputStream);

        // Get the value for a specific key
        String baseUrl = properties.getProperty(specificKey);
        if (baseUrl == null || baseUrl.isEmpty())
            throw new Exception(String.format("Key '%s' was not found in the file 'envParams.properties'", specificKey));
        return baseUrl;
    }
}
