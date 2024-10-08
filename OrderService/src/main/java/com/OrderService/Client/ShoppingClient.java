package com.OrderService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.OrderService.dto.CartDTO;

@FeignClient(name = "shoppingcartservice")
public interface ShoppingClient {
    @PostMapping("/api/cart/{userName}")
    CartDTO sendCartData(@PathVariable String userName);
}

