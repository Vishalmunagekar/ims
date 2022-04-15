package com.app.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
    @NotBlank(message = "RefreshToken is mandatory")
    private String refreshToken;

    @NotBlank(message = "Username is mandatory")
    private String username;
}
