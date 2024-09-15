package com.ProductCatalogService.Service;

import com.ProductCatalogService.Model.Product;
import com.ProductCatalogService.Repository.ProductRepository;
import com.ProductCatalogService.dto.ProductRequest;
import com.ProductCatalogService.dto.ProductResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .build())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    public void addProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .build();
        productRepository.save(product);
    }

}
