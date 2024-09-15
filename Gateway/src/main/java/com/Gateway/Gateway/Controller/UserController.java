package com.Gateway.Gateway.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Gateway.Gateway.Model.User;
import com.Gateway.Gateway.Request.ExtraDetails;
import com.Gateway.Gateway.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @PostMapping("/updateProfile")
    public ResponseEntity<User> updateUserProfile(@RequestHeader("Authorization") String jwt
    ,@RequestBody ExtraDetails extraDetails) throws Exception {
        User user = userService.updateUserDetails(jwt,extraDetails);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
