package com.app.ims.dto;

import com.app.ims.model.Customer;
import com.app.ims.model.OrderDetail;
import com.app.ims.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    @NotBlank(message = "Order description is mandatory")
    private String description;
    private String internalAccountNumber;
    private String externalAccountNumber;
    @NotBlank(message = "Order status is mandatory")
    private OrderStatus status;
    private Date date;
    private Integer TotalItems;
    private Double discount;
    private Double totalPrice;
    private Customer customer;
    private Set<OrderDetail> orderDetailSet = new HashSet<OrderDetail>();
}
