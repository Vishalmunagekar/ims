package com.app.ims.dto;

import com.app.ims.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String contact;
    private Set<Role> roles = new HashSet<>();
}
