package com.ShoppingCartService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ShoppingCartService.Model.Cart;


public interface CartRepository extends JpaRepository<Cart,Long>{
    Cart findByUserName(String userName);
}
