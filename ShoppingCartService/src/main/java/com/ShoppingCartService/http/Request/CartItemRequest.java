package com.ShoppingCartService.http.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
    private Long idUser;
    private Long idProduct;
    private Integer quantity;
}
