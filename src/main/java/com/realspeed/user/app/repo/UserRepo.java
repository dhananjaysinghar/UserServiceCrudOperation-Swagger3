package com.realspeed.user.app.repo;

import com.realspeed.user.app.repo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByPhoneNumberOrEmailId(String phoneNumber, String emailId);
}
