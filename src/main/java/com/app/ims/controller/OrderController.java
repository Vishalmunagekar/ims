package com.app.ims.controller;

import com.app.ims.common.DiscountValueWrongException;
import com.app.ims.common.OrderTotalPriceNotCorrectException;
import com.app.ims.dto.CreateOrderRequest;
import com.app.ims.model.Order;
import com.app.ims.model.OrderDetail;
import com.app.ims.model.Product;
import com.app.ims.repository.OrderRepository;
import com.app.ims.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "api/order")
public class OrderController {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

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
        LOGGER.debug("createOrder : {}",orderRequest.toString());
        Order order = new Order();
        order.setDescription(orderRequest.getDescription());
        order.setExternalAccountNumber(orderRequest.getExternalAccountNumber());
        order.setInternalAccountNumber(orderRequest.getInternalAccountNumber());
        order.setStatus(orderRequest.getStatus());
        order.setDate(new Date());
        order.setTotalItems(orderRequest.getTotalItems());
        validateOrderDiscount(orderRequest);
        Set<OrderDetail> orderDetailSet = orderRequest.getOrderDetailSet();
        validateOrderTotalPrice(orderRequest, order, orderDetailSet);
        order.setDiscount(orderRequest.getDiscount());
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

    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteOrderById(@NotNull @PathVariable Long id){
        orderRepository.deleteById(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    public void validateOrderTotalPrice(CreateOrderRequest orderRequest, Order order, Set<OrderDetail> orderDetailSet) {
        Double orderGrandTotal = Double.valueOf(0);
        List<Long> ids = orderDetailSet.stream().map(orderDetail -> {
            return orderDetail.getProduct().getId();
        }).collect(Collectors.toList());

        //fetch all products at one time
        List<Product> allById = productRepository.findAllById(ids);

        // update and validate values of discount in each orderDetails
        Set<OrderDetail> updatedOrderDetailSet = orderDetailSet.stream().map((orderDetail -> {
            Product product = allById.stream().filter(prdt -> prdt.getId().equals(orderDetail.getProduct().getId())).findFirst().orElseThrow(
                    () -> new EntityNotFoundException("Product not found with id : " + orderDetail.getProduct().getId()));
            orderDetail.setProduct(product);
            return validateOrderDetailDiscount(orderDetail);
        })).collect(Collectors.toSet());

        //validate total order price
        for(OrderDetail orderDetail : updatedOrderDetailSet){
            Integer quantity = orderDetail.getQuantity();
            Double price = orderDetail.getProduct().getPrice();
            Double singleOrderDetailsPrice = quantity * price;
            orderGrandTotal += (singleOrderDetailsPrice * (1 - orderDetail.getDiscount()));
            order.addOrderDetails(orderDetail);
        }
        if(!orderGrandTotal.equals(orderRequest.getTotalPrice())){
            throw new OrderTotalPriceNotCorrectException("Calculated price " + orderGrandTotal + " dose not match with Total Price " + orderRequest.getTotalPrice());
        }
    }

    public OrderDetail validateOrderDetailDiscount(OrderDetail orderDetail) {
        Double discountInPercent = orderDetail.getDiscount();
        Double discount = Double.valueOf(discountInPercent / 100);
        if(!(0.00 <= discount  && 1.0 > discount)){
            throw new DiscountValueWrongException("Wrong discount value : " + discount);
        }
        orderDetail.setDiscount(discount);
        return orderDetail;
    }

    public void validateOrderDiscount(CreateOrderRequest orderRequest) {
        Double discountInPercent = orderRequest.getDiscount();
        Double discount = Double.valueOf(discountInPercent / 100);
        if(!(0.00 <= discount  && 1.0 > discount)){
            throw new DiscountValueWrongException("Wrong discount value : " + discount);
        }
        orderRequest.setDiscount(discount);
    }
}
