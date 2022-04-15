package com.app.ims.controller;

import com.app.ims.common.OrderTotalPriceNotCorrectException;
import com.app.ims.dto.CreateOrderRequest;
import com.app.ims.model.Order;
import com.app.ims.model.OrderDetail;
import com.app.ims.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrderById(@NotNull @PathVariable Long id){
        return new ResponseEntity<Order>(orderRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Order>> getAllOrder(){
        return new ResponseEntity<List<Order>>(orderRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest orderRequest){
        Order order = new Order();
        order.setDescription(orderRequest.getDescription());
        order.setExternalAccountNumber(orderRequest.getExternalAccountNumber());
        order.setInternalAccountNumber(orderRequest.getInternalAccountNumber());
        order.setStatus(orderRequest.getStatus());
        order.setDate(new Date());
        order.setTotalItems(orderRequest.getTotalItems());
        Set<OrderDetail> orderDetailSet = orderRequest.getOrderDetailSet();
        Double orderGrandTotal = Double.valueOf(0);
        for(OrderDetail orderDetail : orderDetailSet){
            Integer quantity = orderDetail.getQuantity();
            Double price = orderDetail.getProduct().getPrice();
            Double singleOrderDetailsPrice = quantity * price;
            orderGrandTotal += singleOrderDetailsPrice * orderDetail.getDiscount();
            order.addOrderDetails(orderDetail);
        }
        if(orderGrandTotal != orderRequest.getTotalPrice()){
            throw new OrderTotalPriceNotCorrectException("Calculated price " + orderGrandTotal + " dose not match with Total Price " + orderRequest.getTotalPrice());
        }
        order.setTotalPrice(orderRequest.getTotalPrice());
        return new ResponseEntity<>(orderRepository.save(order), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateOrderById(@PathVariable Long id, @RequestBody Order order){
        Order optionalOrder = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id : " + id));
        optionalOrder.setDate(new Date());
        optionalOrder.setStatus(order.getStatus());
        optionalOrder.setInternalAccountNumber(order.getInternalAccountNumber());
        optionalOrder.setExternalAccountNumber(order.getExternalAccountNumber());
        optionalOrder.setDescription(order.getDescription());
        optionalOrder.setOrderDetailSet(order.getOrderDetailSet());
        return new ResponseEntity<>(orderRepository.save(optionalOrder), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteOrderById(@NotNull @PathVariable Long id){
        orderRepository.deleteById(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
