package com.pastaebasta.eist.client.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.pastaebasta.eist.server.service.QueryService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * SERVICE - Geocoding Service
 *
 *
 * Handles the conversion of a search by a user into Lat/Lng coordinates.
 * Used as baseline for assuming user list/map search position.
 *
 * @author Sebastian
 */
public class GeocodingService extends QueryService {
    private final String geoURL = "/geocode/json?address=";
    private final GeoApiContext context;
    private final String url;
    private GeocodingResult[] results;
    private Gson gson;

    /**
     * Initializes a context and runs a query based on a user string, pulls in API key and constructs path for searching.
     *
     * @param query Any user input (text)
     * @throws IOException
     * @throws InterruptedException
     * @throws ApiException
     */
    public GeocodingService(String query) throws IOException, InterruptedException, ApiException {
        this.context = new GeoApiContext.Builder()
                .apiKey(super.getApiKey())
                .build();
        this.results = new GeocodingResult[0];
        this.url = getBase_url() + geoURL + URLEncoder.encode(query, StandardCharsets.UTF_8);
        this.runQuery();
    }

    /**
     * Runs a query based on URL and context from constructor, saves to Gson.
     *
     * @return Gson Returns google JSON variant
     */
    public Gson runQuery() {
        try {
            this.setResults(GeocodingApi.geocode(this.getContext(), this.getUrl()).await());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        this.setGson(new GsonBuilder().setPrettyPrinting().create());
        context.shutdown();
        return this.getGson();
    }

    /**
     * Gets Google URL constructed in constructor
     *
     * @return String of URL
     */
    public String getGeoURL() {
        return geoURL;
    }

    /**
     * Gets context for running queries
     *
     * @return GeoAPIContext with API key used for calling
     */
    public GeoApiContext getContext() {
        return context;
    }

    /**
     * Gets an array of results from the query run
     *
     * @return Array of results
     */
    public GeocodingResult[] getResults() {
        return results;
    }

    /**
     * Set GeocodingResults to a new query run
     *
     * @param results
     */
    public void setResults(GeocodingResult[] results) {
        this.results = results;
    }

    /**
     * Gets Gson of results
     *
     * @return Gson
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * Sets Gson to another Gson
     *
     * @param gson
     */
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    /**
     * Get google URL constructed
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }
}
