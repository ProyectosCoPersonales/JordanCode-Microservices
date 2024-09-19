package com.PaymentServicePayPal.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.PaymentServicePayPal.dto.OrderDTO;

@FeignClient(name="OrderService")
public interface OrderClient {

    @PostMapping("/api/orders/bring/{orderId}")
    OrderDTO bringOrder(@PathVariable Long orderId);

    @PostMapping("/api/orders/complete/{orderId}")
    OrderDTO completeOrder(@PathVariable Long orderId);
}

