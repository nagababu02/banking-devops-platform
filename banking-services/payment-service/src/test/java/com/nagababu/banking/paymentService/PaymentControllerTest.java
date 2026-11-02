package com.nagababu.banking.paymentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void statusEndpointReturnsServiceIdentity() throws Exception {
        mockMvc.perform(get("/api/v1/payments/status"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.service").value("payment-service"))
            .andExpect(jsonPath("$.status").value("STARTED"));
    }
}
