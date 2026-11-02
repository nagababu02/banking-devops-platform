package com.nagababu.banking.customerService;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @GetMapping("/status")
    public Map<String, String> status() {
        return Map.of(
            "service", "customer-service",
            "status", "STARTED",
            "message", "Customer service API is available"
        );
    }
}