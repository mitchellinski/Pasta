package com.pastaebasta.eist.server.service;

import com.pastaebasta.eist.server.repository.CustomerRepository;
import com.pastaebasta.eist.shared.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * SERVICE - Customer Service
 *
 * Handles high-level actions on Customer:
 * add, delete, update, etc
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * Gets a customer from the database, if one exists, otherwise creates a new customer.
     * UNSAFE - Testing purposes only
     *
     * @param customerId The customer's id
     * @return The customer
     */
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

    /**
     * Gets all customers in the repository
     * @return Iterable of all customers
     */
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Gets customer by Id, if one exists
     * @param customerId id
     * @return Customer or null
     */
    public Customer getCustomerById(String customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    /**
     * Gets customer by username, if one can be found
     * @param username name
     * @return Customer or null
     */
    public Customer getCustomerByUsername(String username) {
        return customerRepository.getItemByName(username).orElse(null);
    }

    /**
     * Gets customer by e-mail, if one can be found
     * @param email e-mail to be used
     * @return Customer or null
     */
    public Customer getCustomerByEmail(String email) {
        return customerRepository.getByEmail(email).orElse(null);
    }

    /**
     * Create a new Customer on the database
     * @param newCustomer Customer
     * @return Persisted Customer
     */
    public Customer saveCustomer(Customer newCustomer) {
        System.out.println(newCustomer);
        return customerRepository.save(newCustomer);
    }
}
