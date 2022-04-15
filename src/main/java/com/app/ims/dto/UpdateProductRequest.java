package com.app.ims.dto;

import com.app.ims.model.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    @NotBlank(message = "Product name is mandatory")
    private String name;
    @NotBlank(message = "Product code is mandatory")
    private String code;
    @NotBlank(message = "Product type is mandatory")
    private ProductType type;
    @NotNull(message = "Product price is mandatory")
    private Double price;
}
