package com.Gateway.Gateway.Service;

import com.Gateway.Gateway.Model.User;
import com.Gateway.Gateway.Request.ExtraDetails;

public interface UserService {
        User findUserProfileByJwt(String jwt) throws Exception;  
        User findUserByEmail(String email) throws Exception;  
        User findUserById(Long userId) throws Exception;
        User updateUserDetails(String jwt,ExtraDetails extraDetails)throws Exception;
}
