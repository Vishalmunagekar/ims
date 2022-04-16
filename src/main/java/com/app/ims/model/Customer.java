package com.app.ims.model;

import com.app.ims.common.Constants;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = Constants.NAME_REGEXP, flags = Pattern.Flag.UNICODE_CASE, message = "Name must be valid and in this format : FirstName LastName")
    private String name;

    @Column(name = "email", unique=true)
    private String email;

    @Column(name = "contact", unique=true)
    private String contact;
}
