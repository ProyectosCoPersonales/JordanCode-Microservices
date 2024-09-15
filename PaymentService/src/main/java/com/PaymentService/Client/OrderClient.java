package com.PaymentService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.PaymentService.dto.OrderDTO;

@FeignClient(name="ProductCatalogService", url="localhost:8081/products")
public interface OrderClient{

    @PostMapping("/bring/{orderId}")
    public OrderDTO BringOrder(@RequestParam Long orderId);

    @PostMapping("/complete/{orderId}")
    public OrderDTO CompleteOrder(@RequestParam Long orderId);
}