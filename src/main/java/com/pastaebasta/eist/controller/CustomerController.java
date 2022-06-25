package com.pastaebasta.eist.controller;

import com.pastaebasta.eist.model.Customer;
import com.pastaebasta.eist.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<Iterable<Customer>> findAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    /**
     * Returns a customer, if one exists
     * @param customerId the customerId of the reservation
     * @return the customer, if it exists
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if(customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> newCustomer(@RequestBody Customer newCustomer) {
        return new ResponseEntity<>(customerService.saveCustomer(newCustomer), HttpStatus.CREATED);
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<Customer> findCustomerByUsername(@PathVariable String username) {
        Customer customer = customerService.getCustomerByUsername(username);
        if(customer == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerService.getCustomerByUsername(username), HttpStatus.OK);
    }
}
