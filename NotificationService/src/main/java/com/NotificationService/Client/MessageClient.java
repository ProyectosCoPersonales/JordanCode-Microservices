package com.NotificationService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.NotificationService.dto.MessageDTO;

@FeignClient(name ="PaymentServicePayPal" , url ="http://localhost:8090/api/messages")
public interface MessageClient {
    @GetMapping("/{orderId}")
    MessageDTO GetMessageByOrderId(@PathVariable Long orderId);
}
