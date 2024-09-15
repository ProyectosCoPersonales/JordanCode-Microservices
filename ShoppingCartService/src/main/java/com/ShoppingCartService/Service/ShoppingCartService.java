package com.ShoppingCartService.Service;

import com.ShoppingCartService.Model.Cart;
import com.ShoppingCartService.http.Response.CartItemResponse;
import com.ShoppingCartService.http.Response.CartResponse;

public interface ShoppingCartService {
    CartItemResponse CreateCartItem(Long idProduct, Integer quantity);
    CartResponse AddCartItem(Long idUser, Long idProduct, Integer quantity);
    Cart findCartByUser(String userName);
    String RemoveCart(Long idUser);
    CartResponse removeCartItem(Long idUser, Long idProduct);
}
