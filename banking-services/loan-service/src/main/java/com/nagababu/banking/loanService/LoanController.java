package com.nagababu.banking.loanService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
 @GetMapping("/status")
 public Map<String,String> status() { return Map.of("service","loan-service","status","STARTED","message","Loan service API is available"); }
}