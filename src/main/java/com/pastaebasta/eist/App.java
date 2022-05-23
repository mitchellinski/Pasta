package com.pastaebasta.eist;

import com.pastaebasta.eist.controller.ServerController;
import com.pastaebasta.eist.model.Customer;
import com.pastaebasta.eist.model.Reservation;

import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {


        LocalDateTime startTime = LocalDateTime.of(2022, 05, 20, 18, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2022, 05, 20, 19, 00, 00);
        //Just as example, a reservation:
        Reservation dummyReservation = new Reservation("pasteebastaEIST@gmail.com", startTime, endTime, 4, 1234);
        //Customer

        Customer dummyCustomer = new Customer("pastaebastaEIST@gmail.com", null);


        //Initiatialize backend
        ServerController serverController = new ServerController();
        serverController.createReservation(dummyCustomer, 0, startTime, 1234);
//        System.out.println(serverController.getReservationInformation(dummyCustomer, 1234));
        System.out.println("Hi");

    }
}

