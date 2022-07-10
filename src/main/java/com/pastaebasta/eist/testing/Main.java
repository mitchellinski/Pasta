package com.pastaebasta.eist.testing;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.pastaebasta.eist.server.service.FindPlaceService;
import com.pastaebasta.eist.server.service.GeocodingService;
import com.pastaebasta.eist.server.service.NearbySearchService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {


//        GoogleDummy test = new GoogleDummy();
//        System.out.println((test.queryGoogleResults(QueryType.RESTAURANTGENRE, "Italian")).toString());

        try {
//            GeocodingService geoTrial = new GeocodingService("Arcisstrasse 21, 80333, Munich, DE");
//            FindPlaceService locationTrial = new FindPlaceService("Technische Universität München");
            NearbySearchService placesTrial = new NearbySearchService(new LatLng(48.148849, 11.568000));
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
