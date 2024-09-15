package com.ShoppingCartService.http.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
   private String nameUser;
   private String email;
   private List<CartItemResponse> items;
   private Double total;
}
