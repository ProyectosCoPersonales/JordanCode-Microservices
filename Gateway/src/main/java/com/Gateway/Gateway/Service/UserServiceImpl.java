package com.Gateway.Gateway.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gateway.Gateway.Config.JwtProvider;
import com.Gateway.Gateway.Model.User;
import com.Gateway.Gateway.Repository.UserRepository;
import com.Gateway.Gateway.Request.ExtraDetails;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new Exception("user not found");
        }
        return optionalUser.get();
    }

    @Override
    public User updateUserDetails(String jwt,ExtraDetails extraDetails) throws Exception{
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = findUserByEmail(email);
        user.setAddress(extraDetails.getAddress());
        user.setPhone(extraDetails.getPhone());
        return userRepository.save(user);
    }
}
