package com.app.ims.service;

import com.app.ims.common.DiscountValueWrongException;
import com.app.ims.common.OrderTotalPriceNotCorrectException;
import com.app.ims.dto.CreateOrderRequest;
import com.app.ims.model.Customer;
import com.app.ims.model.Order;
import com.app.ims.model.OrderDetail;
import com.app.ims.model.Product;
import com.app.ims.repository.CustomerRepository;
import com.app.ims.repository.OrderRepository;
import com.app.ims.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Order getOrderById(Long id){
        return orderRepository.findById(id).get();
    }

    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

    public Order createOrder(CreateOrderRequest orderRequest){
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
        Optional<Customer> customerByContact = customerRepository.findByContact(orderRequest.getCustomer().getContact());
        if(customerByContact.isPresent()) {
            order.setCustomer(customerByContact.get());
        } else if(!customerByContact.isPresent()) {
            Optional<Customer> customerByEmail = customerRepository.findByEmail(orderRequest.getCustomer().getEmail());
            if(customerByEmail.isPresent()){
                order.setCustomer(customerByEmail.get());
            } else {
                Customer newCustomer = customerRepository.save(orderRequest.getCustomer());
                order.setCustomer(newCustomer);
            }
        }
        return orderRepository.save(order);
    }

    public Order updateOrderById(Long id, Order order){
        LOGGER.debug("updateOrderById id : {} and Order : {}",id, order.toString());
        Order optionalOrder = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with id : " + id));
        optionalOrder.setDate(new Date());
        optionalOrder.setStatus(order.getStatus());
        optionalOrder.setInternalAccountNumber(order.getInternalAccountNumber());
        optionalOrder.setExternalAccountNumber(order.getExternalAccountNumber());
        optionalOrder.setDescription(order.getDescription());
        optionalOrder.setOrderDetailSet(order.getOrderDetailSet());
        return orderRepository.save(optionalOrder);
    }

    public Boolean deleteOrderById(Long id){
        LOGGER.debug("deleteOrderById id : {}",id);
        orderRepository.deleteById(id);
        return Boolean.TRUE;
    }

    public void validateOrderTotalPrice(CreateOrderRequest orderRequest, Order order, Set<OrderDetail> orderDetailSet) {
        LOGGER.debug("In validateOrderTotalPrice method");
        Double orderGrandTotal = Double.valueOf(0);
        List<Long> ids = orderDetailSet.stream().map(orderDetail -> {
            return orderDetail.getProduct().getId();
        }).collect(Collectors.toList());
        LOGGER.debug("product ids :{}", ids.toArray());
        //fetch all products at one time
        List<Product> allById = productRepository.findAllById(ids);

        // update and validate values of discount in each orderDetails
        Set<OrderDetail> updatedOrderDetailSet = orderDetailSet.stream().map((orderDetail -> {
            Product product = allById.stream().filter(p -> p.getId().equals(orderDetail.getProduct().getId())).findFirst().orElseThrow(
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
        LOGGER.debug("In validateOrderDetailDiscount");
        Double discountInPercent = orderDetail.getDiscount();
        Double discount = Double.valueOf(discountInPercent / 100);
        if(!(0.00 <= discount  && 1.0 > discount)){
            throw new DiscountValueWrongException("Wrong discount value : " + discount);
        }
        orderDetail.setDiscount(discount);
        return orderDetail;
    }

    public void validateOrderDiscount(CreateOrderRequest orderRequest) {
        LOGGER.debug("In validateOrderDiscount");
        Double discountInPercent = orderRequest.getDiscount();
        Double discount = Double.valueOf(discountInPercent / 100);
        if(!(0.00 <= discount  && 1.0 > discount)){
            throw new DiscountValueWrongException("Wrong discount value : " + discount);
        }
        orderRequest.setDiscount(discount);
    }
}
