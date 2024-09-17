package com.ShoppingCartService.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ShoppingCartService.Client.ProductClient;
import com.ShoppingCartService.Client.UserClient;
import com.ShoppingCartService.Model.Cart;
import com.ShoppingCartService.Model.CartItem;
import com.ShoppingCartService.Repository.CartRepository;
import com.ShoppingCartService.dto.CartDTO;
import com.ShoppingCartService.dto.CartItemDTO;
import com.ShoppingCartService.dto.ProductDTO;
import com.ShoppingCartService.dto.UserDTO;
import com.ShoppingCartService.http.Response.CartItemResponse;
import com.ShoppingCartService.http.Response.CartResponse;

import jakarta.transaction.Transactional;

@Service
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
                                .productId(product.getId())
                                .nameProduct(product.getName())
                                .quantity(quantity)
                                .unitPrice(product.getPrice())
                                .build();
        }

        @Transactional
        @Override
        public CartResponse AddCartItem(Long idUser, Long idProduct, Integer quantity) {
                UserDTO user = userClient.getUserById(idUser);
                Cart cart = findCartByUser(user.getName());
                if (cart == null) {
                        cart = Cart.builder()
                                        .userName(user.getName())
                                        .email(user.getEmail())
                                        .total(0.0)
                                        .cartItems(new ArrayList<>())
                                        .build();
                        cartRepository.save(cart);
                }
                CartItem existingItem = cart.getCartItems().stream()
                                .filter(item -> item.getProductId().equals(idProduct))
                                .findFirst()
                                .orElse(null);

                if (existingItem != null) {
                        existingItem.setQuantity(existingItem.getQuantity() + quantity);
                } else {
                        CartItemResponse cartItemResponse = CreateCartItem(idProduct, quantity);
                        if (cartItemResponse != null) {
                                CartItem cartItem = CartItem.builder()
                                                .cart(cart)
                                                .productId(idProduct)
                                                .nameProduct(cartItemResponse.getNameProduct())
                                                .quantity(quantity)
                                                .unitPrice(cartItemResponse.getUnitPrice())
                                                .build();
                                cart.getCartItems().add(cartItem);
                        } else {
                                throw new RuntimeException("Error al obtener el producto con id: " + idProduct);
                        }
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
                                                .productId(item.getProductId())
                                                .nameProduct(item.getNameProduct())
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
                                                .productId(item.getProductId())
                                                .nameProduct(item.getNameProduct())
                                                .quantity(item.getQuantity())
                                                .unitPrice(item.getUnitPrice())
                                                .build())
                                                .toList())
                                .total(cart.getTotal())
                                .build();
        }

        @Override
        public CartDTO sendCartData(String userName) {
                Cart cart = findCartByUser(userName);
                List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream()
                                .map(item -> CartItemDTO.builder()
                                                .productId(item.getProductId())
                                                .quantity(item.getQuantity())
                                                .unitPrice(item.getUnitPrice())
                                                .build())
                                .collect(Collectors.toList());
                return CartDTO.builder()
                                .id(cart.getId())
                                .userName(cart.getUserName())
                                .total(cart.getTotal())
                                .cartItems(cartItemDTOs)
                                .build();
        }

}
