package com.tranv.d7shop.services;

import com.tranv.d7shop.dtos.UserDTO;
import com.tranv.d7shop.exceptions.DataNotFoundException;
import com.tranv.d7shop.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;

    String loginUser(String phoneNumber, String password) throws Exception;
}
