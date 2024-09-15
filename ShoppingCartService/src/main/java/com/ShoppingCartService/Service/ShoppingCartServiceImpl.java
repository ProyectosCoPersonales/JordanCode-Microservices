package com.ShoppingCartService.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.ShoppingCartService.Client.ProductClient;
import com.ShoppingCartService.Client.UserClient;
import com.ShoppingCartService.Model.Cart;
import com.ShoppingCartService.Model.CartItem;
import com.ShoppingCartService.Repository.CartRepository;
import com.ShoppingCartService.dto.ProductDTO;
import com.ShoppingCartService.dto.UserDTO;
import com.ShoppingCartService.http.Response.CartItemResponse;
import com.ShoppingCartService.http.Response.CartResponse;

public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserClient userClient;

    @Override
    public Cart findCartByUser(String userName) {
        return cartRepository.findByUserName(userName);
    }

    @Override
    public CartItemResponse CreateCartItem(Long idProduct, Integer quantity) {
        ProductDTO product = productClient.getProductById(idProduct);
        return CartItemResponse.builder()
                .nameProduct(product.getName())
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .build();
    }

    @Override
    public CartResponse AddCartItem(Long idUser, Long idProduct, Integer quantity) {
        UserDTO user = userClient.getUserById(idUser);
        Cart cart = findCartByUser(user.getName());
        if (cart == null) {
            cart = Cart.builder()
                    .userName(user.getName())
                    .total(0.0)
                    .cartItems(new ArrayList<>())
                    .build();
        }

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(idProduct))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItemResponse cartItemResponse = CreateCartItem(idProduct, quantity);
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .productId(idProduct)
                    .quantity(quantity)
                    .unitPrice(cartItemResponse.getUnitPrice())
                    .build();
            cart.getCartItems().add(cartItem);
        }
        Double newTotal = cart.getCartItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();
        cart.setTotal(newTotal);
        cartRepository.save(cart);
        return CartResponse.builder()
                .nameUser(user.getName())
                .email(user.getEmail())
                .items(cart.getCartItems().stream().map(item -> CartItemResponse.builder()
                        .nameProduct(productClient.getProductById(item.getProductId()).getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                        .toList())
                .total(cart.getTotal())
                .build();
    }

    @Override
    public String RemoveCart(Long idUser) {
        UserDTO user = userClient.getUserById(idUser);
        Cart cart = findCartByUser(user.getName());
        cartRepository.deleteById(cart.getId());
        return "Delete success";
    }

    @Override
    public CartResponse removeCartItem(Long idUser, Long idProduct) {
        UserDTO user = userClient.getUserById(idUser);
        Cart cart = findCartByUser(user.getName());
        if (cart == null) {
            throw new RuntimeException("No se encontró un carrito para el usuario: " + user.getName());
        }
        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(idProduct))
                .findFirst()
                .orElse(null);

        if (itemToRemove == null) {
            throw new RuntimeException("No se encontró el producto en el carrito: " + idProduct);
        }
        cart.getCartItems().remove(itemToRemove);
        Double newTotal = cart.getCartItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();
        cart.setTotal(newTotal);
        cartRepository.save(cart);


        return CartResponse.builder()
                .nameUser(user.getName())
                .email(user.getEmail())
                .items(cart.getCartItems().stream().map(item -> CartItemResponse.builder()
                        .nameProduct(productClient.getProductById(item.getProductId()).getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                        .toList())
                .total(cart.getTotal())
                .build();
    }

}
