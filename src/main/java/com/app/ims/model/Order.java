package com.app.ims.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ApiModelProperty(value = "Account Number in this system")
    private String internalAccountNumber;
    @ApiModelProperty(value = "Account Number in an external system")
    private String externalAccountNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Date date;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Set<OrderDetail> orderDetailSet = new HashSet<OrderDetail>();

    public void addOrderDetails(OrderDetail orderDetail){
        this.orderDetailSet.add(orderDetail);
    }

    public void addOrderDetails(Set<OrderDetail> orderDetails){
        orderDetails.stream().map(orderDetail -> this.orderDetailSet.add(orderDetail));
    }

}
