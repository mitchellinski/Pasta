package com.pastaebasta.eist.server.controller;

import com.pastaebasta.eist.server.service.CustomerService;
import com.pastaebasta.eist.shared.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Customer Controller
 * Exposes API to the client, which is used to communicate with the server.
 * <p>
 * This is used internally by Spring Boot to map requests automatically.
 *
 * @author Hisashi
 * @author Michael
 * @version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private CustomerService customerService;

    /**
     * Gets all the customers in the database
     *
     * @return Iterable of customers
     */
    @GetMapping
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    /**
     * Returns a customer, if one exists
     *
     * @param customerId the customerId of the reservation
     * @return the customer, if it exists
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(customer);
    }

    /**
     * Gets a customer by username, if found
     *
     * @param username customer username
     * @return Customer, or null
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<Customer> getCustomerByUsername(@PathVariable String username) {
        Customer customer = customerService.getCustomerByUsername(username);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    /**
     * Gets a custoemr by e-mail, if found
     *
     * @param email customer e-mail
     * @return Customer, or null
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    /**
     * Saves/adds a customer to the database
     *
     * @param newCustomer Customer to be added
     * @return Persisted Customer
     */
    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer newCustomer) {
        return new ResponseEntity<>(customerService.saveCustomer(newCustomer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer updatedCustomer, @PathVariable String id) {
        if (!updatedCustomer.getCustomerId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(customerService.saveCustomer(updatedCustomer));
    }
}
