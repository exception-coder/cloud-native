package cn.exceptioncode.api.doc.client.util;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;



public class OkHttpUtil {


    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");


    private static OkHttpClient httpClient = new OkHttpClient();

    static String post(String url, String json ) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

    static String post(String url, String json, Map<String,String> header ) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .post(body);
        header.forEach((s, s2) -> reqBuilder.addHeader(s,s2));
        try (Response response = httpClient.newCall(reqBuilder.build()).execute()) {
            return response.body().string();
        }
    }


    static Response getResWithPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return response;
        }
    }


}
