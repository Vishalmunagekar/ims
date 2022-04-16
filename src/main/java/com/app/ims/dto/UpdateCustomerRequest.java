package com.app.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequest {
    @NotBlank(message = "Customer name is mandatory")
    private String name;
    @NotBlank(message = "Customer email is mandatory")
    private String email;
    @NotBlank(message = "Customer contact is mandatory")
    private String contact;

}
