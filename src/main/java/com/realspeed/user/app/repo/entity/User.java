package com.realspeed.user.app.repo.entity;

import com.realspeed.user.app.utils.Constants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(generator = Constants.UUID)
    @GenericGenerator(name = Constants.UUID, strategy = Constants.UUID)
    private String uId;

    private String firstName;

    private String lastName;

    private String emailId;

    private String dob;

    private String phoneNumber;

    private String address;

    private boolean active = true;
}