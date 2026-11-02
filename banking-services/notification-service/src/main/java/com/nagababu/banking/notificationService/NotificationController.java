package com.nagababu.banking.notificationService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
 @GetMapping("/status")
 public Map<String,String> status() { return Map.of("service","notification-service","status","STARTED","message","Notification service API is available"); }
}