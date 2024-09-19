package com.NotificationService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.NotificationService.dto.MessageDTO;

@FeignClient(name = "PaymentServicePayPal")
public interface MessageClient {
    @GetMapping("/api/messages/{orderId}")
    MessageDTO GetMessageByOrderId(@PathVariable Long orderId);
}
