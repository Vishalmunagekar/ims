package com.app.ims.controller;

import com.app.ims.dto.CreateOrderRequest;
import com.app.ims.model.Order;
import com.app.ims.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api/order")
public class OrderController {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrderById(@NotNull @PathVariable Long id){
        return new ResponseEntity<Order>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Order>> getAllOrder(){
        return new ResponseEntity<List<Order>>(orderService.getAllOrder(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest orderRequest){
        LOGGER.debug("createOrder : {}",orderRequest.toString());
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateOrderById(@PathVariable Long id, @RequestBody Order order){
        LOGGER.debug("updateOrderById id : {} and Order : {}", id, order.toString());
        return new ResponseEntity<>(orderService.updateOrderById(id, order), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteOrderById(@NotNull @PathVariable Long id){
        LOGGER.debug("deleteOrderById id : {} ", id);
        return new ResponseEntity<Boolean>(orderService.deleteOrderById(id), HttpStatus.OK);
    }
}
