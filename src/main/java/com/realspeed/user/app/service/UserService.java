package com.realspeed.user.app.service;

import com.realspeed.user.app.model.ResponseObject;
import com.realspeed.user.app.model.UpdateUserDto;
import com.realspeed.user.app.model.UserDto;


public interface UserService {

    ResponseObject saveUser(UserDto userDto);

    ResponseObject findUser(String uId);

    ResponseObject findUserBy(String phoneNumber, String emailId);

    ResponseObject updateUser(String uId, UpdateUserDto updateUserDto);

    ResponseObject deActivateUser(String uId);

    ResponseObject deleteUser(String uId);

}
