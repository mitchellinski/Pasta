package com.pastaebasta.eist.service;

import com.pastaebasta.eist.model.Customer;
import com.pastaebasta.eist.repository.CustomerRepository;
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

    public Customer getCustomerById(String customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    public Customer saveCustomer(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    public Customer getCustomerByUsername(String username) {
        return customerRepository.getItemByName(username).orElse(null);
    }

    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
