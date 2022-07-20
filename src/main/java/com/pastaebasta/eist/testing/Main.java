package com.pastaebasta.eist.testing;

import com.google.api.client.json.Json;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PriceLevel;
import com.pastaebasta.eist.server.service.GeocodingService;
import com.pastaebasta.eist.server.service.NearbySearchService;
import com.pastaebasta.eist.server.service.StaticMapsService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {


//        GoogleDummy test = new GoogleDummy();
//        System.out.println((test.queryGoogleResults(QueryType.RESTAURANTGENRE, "Italian")).toString());

        try {
            GeocodingService geoTrial = new GeocodingService("Arcisstrasse 21, 80333, Munich, DE");
//            FindPlaceService locationTrial = new FindPlaceService("Technische Universität München");
//            NearbySearchService placesTrial = new NearbySearchService(new LatLng(48.148849, 11.568000),"Steak",5000, PriceLevel.MODERATE);
//            Json x = new Json(placesTrial.getResults());
//            StaticMapsService mappy = new StaticMapsService("Technical University Munich");
            System.out.println("Success!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }


    }


}
