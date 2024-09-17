package com.PaymentServicePayPal.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.PaymentServicePayPal.dto.OrderDTO;

@FeignClient(name="OrderService", url="localhost:8090/api/orders")
public interface OrderClient{

    @PostMapping("/bring/{orderId}")
    OrderDTO BringOrder(@PathVariable Long orderId);

    @PostMapping("/complete/{orderId}")
    OrderDTO CompleteOrder(@PathVariable Long orderId);
}
