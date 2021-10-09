package com.realspeed.user.app.model;

import com.realspeed.user.app.utils.Constants;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserDto {
    @NotNull(message = "firstName should not be null")
    private String firstName;

    @NotNull(message = "lastName should not be null")
    private String lastName;

    @Email(message = "Email should be valid")
    private String emailId;

    @Pattern(regexp = Constants.DOB_REGEX, message = "date of birth format should be YYYY-MM-DD")
    private String dob;

    @NotNull(message = "phoneNumber should not be null")
    @Pattern(regexp = Constants.MOBILE_NUMBER_REGEX, message =
            Constants.MOBILE_NUMBER_REGEX_ERROR_MESSAGE)
    private String phoneNumber;

    @NotNull(message = "address should not be null")
    private String address;
}
