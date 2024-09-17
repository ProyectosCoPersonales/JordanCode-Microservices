package com.Authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Authentication.Model.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
