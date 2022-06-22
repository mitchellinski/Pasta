package com.pastaebasta.eist;

import com.pastaebasta.eist.controller.ClientController;
import com.pastaebasta.eist.model.Customer;

public class ClientControllerTest {
    private ClientController clientController;
    private Customer customer;

    public ClientControllerTest() {
        clientController = new ClientController();
    }

    public Customer getCustomerTest() {
        return customer = clientController.getCustomer("1");
    }

    public static void main(String[] args) {
        ClientControllerTest test = new ClientControllerTest();
        Customer testCustomer = test.getCustomerTest();
        System.out.println(testCustomer);
    }
}
