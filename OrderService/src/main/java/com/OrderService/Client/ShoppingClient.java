package com.OrderService.Client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ShoppingCartService", url = "localhost:8082/")
public interface ShoppingClient {
    
}
