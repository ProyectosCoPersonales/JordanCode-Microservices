package com.ShoppingCartService.Controller;

import com.ShoppingCartService.Model.Cart;
import com.ShoppingCartService.Service.ShoppingCartService;
import com.ShoppingCartService.dto.CartDTO;
import com.ShoppingCartService.http.Request.CartItemRequest;
import com.ShoppingCartService.http.Response.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/find")
    public ResponseEntity<Cart> findCartByUser(@RequestBody String userName){
        Cart cart = shoppingCartService.findCartByUser(userName);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addCartItem(@RequestBody CartItemRequest cartItemRequest) {
        CartResponse cartResponse = shoppingCartService.AddCartItem(cartItemRequest.getIdUser(), cartItemRequest.getIdProduct(), cartItemRequest.getQuantity());
        return ResponseEntity.ok(cartResponse);
    }


    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeCart(@RequestParam Long idUser) {
        String result = shoppingCartService.RemoveCart(idUser);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/item/remove")
    public ResponseEntity<CartResponse> removeCartItem(@RequestBody CartItemRequest cartItemRequest) {
        CartResponse cartResponse = shoppingCartService.removeCartItem(cartItemRequest.getIdUser(), cartItemRequest.getIdProduct());
        return ResponseEntity.ok(cartResponse);
    }


    @PostMapping("/{userName}")
    CartDTO sendCartData(@PathVariable String userName){
        return shoppingCartService.sendCartData(userName);
    }
}
