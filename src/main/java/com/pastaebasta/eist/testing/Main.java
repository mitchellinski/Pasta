package com.pastaebasta.eist.testing;

import com.pastaebasta.eist.model.Google;
import com.pastaebasta.eist.model.QueryType;

public class Main {

    public static void main(String[] args) {


        Google test = new Google();
        System.out.println((test.queryGoogleResults(QueryType.RESTAURANTGENRE,"Italian")).toString());
    }



}
