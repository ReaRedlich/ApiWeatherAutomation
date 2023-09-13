package org.apiHelper;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import okhttp3.ResponseBody;
import retrofit2.Response;
import java.io.IOException;

public class Deserialization {

    public <T> T getObject(Response<ResponseBody> response, Class<T> objectClass) throws IOException {
        return getObject(response, objectClass, "$");
    }

    public <T> T getObject(Response<ResponseBody> response, Class<T> objectClass, String jsonPath) throws IOException {
        String json = getRawBody(response);
        System.out.println("Response Body: " + json);
        return JsonPath.using(gsonConf).parse(json).read(jsonPath, objectClass);
    }

    private String getRawBody(Response<ResponseBody> response) throws IOException {
        return response.body() != null ? response.body().string() : null;
    }

    private static final Configuration gsonConf = Configuration
            .builder()
            .jsonProvider(new GsonJsonProvider())
            .mappingProvider(new GsonMappingProvider())
            .build();
}
