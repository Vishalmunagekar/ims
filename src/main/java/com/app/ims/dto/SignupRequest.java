package com.app.ims.dto;

import com.app.ims.common.Constants;
import com.app.ims.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = Constants.PASSWORD_REGEXP, flags = Pattern.Flag.UNICODE_CASE)
    private String password;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotBlank(message = "Contact is mandatory")
    private String contact;
    private Set<Role> roles = new HashSet<>();
}
