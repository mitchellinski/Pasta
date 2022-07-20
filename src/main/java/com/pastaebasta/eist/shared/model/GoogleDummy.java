package com.pastaebasta.eist.shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GoogleDummy {
    private final ArrayList<Restaurant> googleResult;

    public GoogleDummy() {
        this.googleResult = new ArrayList<Restaurant>();
        createDummyRestaurants();
    }

    private PriceCategory getRandomPriceCategory(int i) {
        i %= 4;
        switch (i) {
            case 0:
                return PriceCategory.VERY_EXPENSIVE;
            case 1:
                return PriceCategory.EXPENSIVE;
            case 2:
                return PriceCategory.NORMAL;
            case 3:
                return PriceCategory.CHEAP;
        }
        return null;
    }

    private String getRandomGenre(int i) {
        i %= 5;
        switch (i) {
            case 0:
                return "Asian";
            case 1:
                return "Italian";
            case 2:
                return "Mexican";
            case 3:
                return "German";
            case 4:
                return "Peruvian";
        }
        return null;
    }
    public double getRandomRating(int i){
        Random random = new Random();
        return Math.round(random.nextDouble(1,6) * 10) / 10; // [1, 5.999...]
    }

    public void createDummyRestaurants() {
//        for (int i = 0; i < 1000; i++) {
//            LocalDateTime startTime = LocalDateTime.of(2022, 05, 20, 8, 00, 00);
//            LocalDateTime endTime = LocalDateTime.of(2022, 05, 20, 20, 00, 00);
//            Restaurant restaurant = new Restaurant(
//                    "restaurant_"+i,
//                    "Name" + i,
//                    "Address" + i,
//                    getRandomGenre(i),
//                    null,
//                    getRandomRating(i),
//                    getRandomPriceCategory(i),
//                    null,
//                    startTime,
//                    endTime,
//                    new Point(i, i),
//                    i % 10,
//                    new ArrayList<DateTimeSlot>()
//            );

//            googleResult.add(restaurant);
//        }
    }

    public List queryGoogleResults(QueryType type, String query){
        switch (type) {
            case ADDRESS:
                return googleResult.stream().filter(s -> s.getAddress().equals(query)).limit(50).collect(Collectors.toList());
            case NAME:
                return googleResult.stream().filter(s -> s.getName().equals(query))
                        .limit(50).
                        collect(Collectors.toList());
            case RATINGAVERAGE:
                return googleResult.stream()
                        .filter(s -> (Math.abs(s.getRatingAverage() - (Double.parseDouble(query))) < 0.000000001))
                        .limit(50)
                        .collect(Collectors.toList());
            case RESTAURANTGENRE:
                return googleResult.stream().filter(s -> s.getRestaurantGenre().equals(query)).limit(50).collect(Collectors.toList());
            default:
                return null;
        }

    }

}
