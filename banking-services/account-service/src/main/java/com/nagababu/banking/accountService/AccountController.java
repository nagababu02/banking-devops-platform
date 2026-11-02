package com.nagababu.banking.accountService;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @GetMapping("/status")
    public Map<String, String> status() {
        return Map.of(
            "service", "account-service",
            "status", "STARTED",
            "message", "Account service API is available"
        );
    }
}