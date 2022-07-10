package com.pastaebasta.eist.server.service;

import com.pastaebasta.eist.shared.model.Customer;
import com.pastaebasta.eist.server.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer getCustomer_FORCE_CREATE(String customerId) {
        return customerRepository.findById(customerId).orElseGet(() -> {
            // THIS IS FOR TESTING PURPOSES ONLY!
            // UNSECURE WAY TO CREATE A CUSTOMER
            System.err.println(">>>> NOOOOOOOO");
            return customerRepository.save(
                    new Customer(
                            customerId,
                            "user_" + customerId,
                            null,
                            "password"
                    )
            );
        });
    }

    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(String customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    public Customer getCustomerByUsername(String username) {
        return customerRepository.getItemByName(username).orElse(null);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.getByEmail(email).orElse(null);
    }

    public Customer saveCustomer(Customer newCustomer) {
        System.out.println(newCustomer);
        return customerRepository.save(newCustomer);
    }
}
