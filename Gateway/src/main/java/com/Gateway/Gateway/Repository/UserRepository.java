package com.Gateway.Gateway.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gateway.Gateway.Model.User;


public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);    
}
