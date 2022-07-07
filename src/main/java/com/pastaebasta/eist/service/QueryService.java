package com.pastaebasta.eist.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.IOException;

public abstract class QueryService {
    private static String base_url = "https://maps.googleapis.com/maps/api";
    private final String apiKey = "AIzaSyDs-IHgDUlF3ZHzK2ATjLiGZs9_NnYXr6U";

    /*
    Initializes basic builder for query
     */
    public QueryService() throws IOException, InterruptedException, ApiException {

    }


    public String getApiKey() {
        return apiKey;
    }

    public static String getBase_url() {
        return base_url;
    }
}