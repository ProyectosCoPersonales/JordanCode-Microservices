package com.OrderService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.OrderService.dto.CartDTO;

@FeignClient(name = "ShoppingCartService", url = "localhost:8082/cart")
public interface ShoppingClient {
    @PostMapping("/{userName}")
    CartDTO sendCartData(@PathVariable String userName);
}
