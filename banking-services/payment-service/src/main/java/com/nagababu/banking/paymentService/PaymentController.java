package com.nagababu.banking.paymentService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
 @GetMapping("/status")
 public Map<String,String> status() { return Map.of("service","payment-service","status","STARTED","message","Payment service API is available"); }
}