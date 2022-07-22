package com.pastaebasta.eist.server.service;

import com.google.maps.errors.ApiException;

import java.io.IOException;

public abstract class QueryService {
    private static final String base_url = "https://maps.googleapis.com/maps/api";
    private final String apiKey = "AIzaSyDs-IHgDUlF3ZHzK2ATjLiGZs9_NnYXr6U";

    /**
    Initializes basic builder for query
     */
    public QueryService() throws IOException, InterruptedException, ApiException {

    }


    /**
     * Gets API key
     * @return
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Gets base url
     * @return
     */
    public static String getBase_url() {
        return base_url;
    }
}