package com.realspeed.user.app.model;

import com.realspeed.user.app.utils.Constants;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UpdateUserDto {

    private String firstName;

    private String lastName;

    private String emailId;

    @Pattern(regexp = Constants.DOB_REGEX, message = "date of birth format should be YYYY-MM-DD")
    private String dob;

    @Pattern(regexp = Constants.MOBILE_NUMBER_REGEX, message =
            Constants.MOBILE_NUMBER_REGEX_ERROR_MESSAGE)
    private String phoneNumber;

    private String address;
}
