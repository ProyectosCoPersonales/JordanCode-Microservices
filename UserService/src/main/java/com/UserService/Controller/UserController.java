package com.UserService.Controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.UserService.Service.UserService;
import com.UserService.dto.UserRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> AllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/info/{idUser}")
    public ResponseEntity<?> getAllInformationById(@PathVariable Long idUser){
        return ResponseEntity.ok(userService.findUserById(idUser));
    }

    @PostMapping("/addDetails/{idUser}")
    public ResponseEntity<?> updateUser(@PathVariable Long idUser,@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.updateUser(idUser, userRequest));
    }
}
