package com.nagababu.banking.transactionService;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @GetMapping("/status")
    public Map<String, String> status() {
        return Map.of(
            "service", "transaction-service",
            "status", "STARTED",
            "message", "Transaction service API is available"
        );
    }
}