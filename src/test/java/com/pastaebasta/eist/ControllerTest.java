package com.pastaebasta.eist;

import com.pastaebasta.eist.server.controller.CustomerController;
import com.pastaebasta.eist.server.security.SecurityConfiguration;
import com.pastaebasta.eist.server.service.CustomerService;
import com.pastaebasta.eist.shared.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
@ContextConfiguration(classes = {CustomerController.class, SecurityConfiguration.class})
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

    @Test
    public void testCreateCustomer() throws Exception {
		Customer customer = new Customer("customerName", "customerEmail", "customerPassword");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/api/v1/customers", customer);
        mockMvc.perform(requestBuilder)
			.andExpect(status().isBadRequest());
    }
}