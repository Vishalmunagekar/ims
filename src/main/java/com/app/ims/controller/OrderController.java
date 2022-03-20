package com.app.ims.controller;

import com.app.ims.model.Order;
import com.app.ims.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        return new ResponseEntity<Order>(orderRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Order>> getAllOrder(){
        return new ResponseEntity<List<Order>>(orderRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody Order order){
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
    public ResponseEntity<?> deleteOrderById(@PathVariable Long id){
        orderRepository.deleteById(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
