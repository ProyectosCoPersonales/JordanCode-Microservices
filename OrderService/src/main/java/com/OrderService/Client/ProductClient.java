package com.OrderService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.OrderService.dto.ProductDTO;

@FeignClient(name = "ProductCatalogService", url = "localhost:8090/api/products")
public interface ProductClient {
    @GetMapping("/{id}")
    ProductDTO getProductById(@PathVariable Long id);
}
