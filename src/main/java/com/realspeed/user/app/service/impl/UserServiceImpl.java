package com.realspeed.user.app.service.impl;

import com.realspeed.user.app.exception.InternalStandardError;
import com.realspeed.user.app.exception.UserServiceException;
import com.realspeed.user.app.model.ResponseObject;
import com.realspeed.user.app.model.UpdateUserDto;
import com.realspeed.user.app.model.UserDto;
import com.realspeed.user.app.repo.UserRepo;
import com.realspeed.user.app.repo.entity.User;
import com.realspeed.user.app.service.UserService;
import com.realspeed.user.app.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public ResponseObject saveUser(UserDto userDto) {
        log.debug("save user called..");
        if (isUserExist(userDto.getPhoneNumber(), userDto.getEmailId())) {
            log.error("user already exist");
            throw new UserServiceException(InternalStandardError.USER_ALREADY_EXIST);
        }
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        User savedUser = userRepo.save(user);
        log.info("user saved successfully : {}", savedUser.getUId());
        return ResponseObject.builder().status("User Saved with id: " + savedUser.getUId()).build();
    }

    @Override
    public ResponseObject findUser(String uId) {
        Optional<User> userOpt = userRepo.findById(uId);
        if (!isActiveUser(userOpt)) {
            log.error("user not found with id {}", uId);
            throw new UserServiceException(InternalStandardError.USER_NOT_FOUND);
        }
        log.info("retrieving user info : {}", uId);
        return ResponseObject.builder().data(userOpt.get()).build();
    }

    @Override
    public ResponseObject findUserBy(String phoneNumber, String emailId) {
        Optional<User> userOpt = userRepo.findByPhoneNumberOrEmailId(phoneNumber, emailId);
        if (!isActiveUser(userOpt)) {
            log.error("user not found");
            throw new UserServiceException(InternalStandardError.USER_NOT_FOUND);
        }
        log.info("retrieving user info : {}", userOpt.get().getUId());
        return ResponseObject.builder().data(userOpt.get()).build();
    }

    @Override
    public ResponseObject updateUser(String uId, UpdateUserDto updateUserDto) {
        User user = (User) findUser(uId).getData();
        CommonUtils.copyValues(updateUserDto, user);
        User savedUser = userRepo.save(user);
        log.info("user updated successfully : {}", savedUser.getUId());
        return ResponseObject.builder()
                .status("user updated successfully : " + savedUser.getUId())
                .data(savedUser)
                .build();
    }

    @Override
    public ResponseObject deActivateUser(String uId) {
        User user = (User) findUser(uId).getData();
        user.setActive(false);
        log.info("deactivated user : {}", uId);
        userRepo.save(user);
        return ResponseObject.builder().data("deactivated user successfully : " + uId).build();
    }

    @Override
    public ResponseObject deleteUser(String uId) {
        Optional<User> userOpt = userRepo.findById(uId);
        if (!userOpt.isPresent()) {
            log.error("user not found");
            throw new UserServiceException(InternalStandardError.USER_NOT_FOUND);
        }
        userRepo.delete(userOpt.get());
        log.info("deleted user : {}", uId);
        return ResponseObject.builder().data("User deleted Successfully").build();
    }

    private boolean isActiveUser(Optional<User> userOpt) {
        boolean isExist = userOpt.isPresent();
        if (isExist && !userOpt.get().isActive()) {
            log.info("deactivated user : {}", userOpt.get().getUId());
            throw new UserServiceException(InternalStandardError.USER_DEACTIVATED);
        }

        return isExist;
    }


    private boolean isUserExist(String phoneNumber, String emailId) {
        return userRepo.findByPhoneNumberOrEmailId(phoneNumber, emailId).isPresent();
    }
}
