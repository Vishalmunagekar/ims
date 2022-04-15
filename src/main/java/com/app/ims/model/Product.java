package com.app.ims.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @NotBlank(message = "Product name is mandatory")
    @Column(name = "name", unique=true)
    private String name;

    @Size(max = 16)
    @NotBlank(message = "Product name is mandatory")
    @Column(name = "code", unique=true)
    private String code;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    private Double price;
}
