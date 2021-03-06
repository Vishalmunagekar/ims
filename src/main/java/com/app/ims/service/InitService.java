package com.app.ims.service;

import com.app.ims.common.Constants;
import com.app.ims.model.*;
import com.app.ims.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class InitService {

    private final Logger LOGGER = LoggerFactory.getLogger(InitService.class);

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;

    public Boolean init1(){
        Role role1 = new Role();
        role1.setRoleName(Constants.USER_ROLE);
        role1.setDescription("User Description");
        roleRepository.save(role1);
        Role role2 = new Role();
        role2.setRoleName(Constants.ADMIN_ROLE);
        role2.setDescription("Admin Description");
        roleRepository.save(role2);

        Optional<Role> USER_ROLE = roleRepository.findByRoleName(Constants.USER_ROLE);
        Optional<Role> ADMIN_ROLE = roleRepository.findByRoleName(Constants.ADMIN_ROLE);

        User user1 = new User();
        user1.setFirstName("Vishal");
        user1.setLastName("Munagekar");
        user1.setContact("7843052772");
        user1.setUsername("vishalm");
        user1.setPassword(passwordEncoder.encode("qwertyui"));

        user1.addRoles(USER_ROLE.get());
        user1.addRoles(ADMIN_ROLE.get());
        userRepository.save(user1);

        Product product1 = new Product();
        product1.setName("IPHONE");
        product1.setCode("01");
        product1.setPrice(Double.valueOf(50000));
        product1.setType(ProductType.R);

        Product product2 = new Product();
        product2.setName("ONEPLUS");
        product2.setCode("02");
        product2.setPrice(Double.valueOf(30000));
        product2.setType(ProductType.L);

        productRepository.save(product1);
        productRepository.save(product2);

        Order order = new Order();
        order.setDate(new Date());
        order.setDescription("Order01 Description");
        order.setExternalAccountNumber("221533644789");
        order.setInternalAccountNumber("221533648652");
        order.setStatus(OrderStatus.finalized);
        order.setTotalItems(3);
        order.setDiscount(Double.valueOf(0.10));
        order.setTotalPrice(Double.valueOf(110000));

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProduct(product1);
        orderDetail1.setDiscount(Double.valueOf(0.15));
        orderDetail1.setPrice(Double.valueOf(50000));
        orderDetail1.setQuantity(1);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProduct(product2);
        orderDetail2.setDiscount(Double.valueOf(0.15));
        orderDetail2.setPrice(Double.valueOf(60000));
        orderDetail2.setQuantity(2);

        Customer customer = new Customer();
        customer.setName("Marc Witter");
        customer.setEmail("marcwitter@gmail.com");
        customer.setContact("9922581049");
        Customer save = customerRepository.save(customer);
        order.setCustomer(save);

        order.addOrderDetails(orderDetail1);
        order.addOrderDetails(orderDetail2);

        orderRepository.save(order);
        return Boolean.TRUE;
    }

    public Boolean init2(){
        Optional<Role> ADMIN_ROLE = roleRepository.findByRoleName(Constants.ADMIN_ROLE);
        User user2 = new User();
        user2.setFirstName("Tony");
        user2.setLastName("Stark");
        user2.setContact("7843052773");
        user2.setUsername("tonys");
        user2.setPassword(passwordEncoder.encode("qwertyui"));
        user2.addRoles(ADMIN_ROLE.get());
        userRepository.save(user2);

        Product product1 = new Product();
        product1.setName("PATANJALI");
        product1.setCode("03");
        product1.setPrice(Double.valueOf(3000));
        product1.setType(ProductType.C);

        Product product2 = new Product();
        product2.setName("WATER");
        product2.setCode("04");
        product2.setPrice(Double.valueOf(2000));
        product2.setType(ProductType.M);

        productRepository.save(product1);
        productRepository.save(product2);

        Order order = new Order();
        order.setDate(new Date());
        order.setDescription("Order02 Description");
        order.setExternalAccountNumber("221533644789");
        order.setInternalAccountNumber("221533648652");
        order.setStatus(OrderStatus.finalized);
        order.setTotalItems(3);
        order.setDiscount(Double.valueOf(0.10));
        order.setTotalPrice(Double.valueOf(5000));

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProduct(product1);
        orderDetail1.setDiscount(Double.valueOf(0.15));
        orderDetail1.setPrice(Double.valueOf(3000));
        orderDetail1.setQuantity(1);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProduct(product2);
        orderDetail2.setDiscount(Double.valueOf(0.15));
        orderDetail2.setPrice(Double.valueOf(4000));
        orderDetail2.setQuantity(2);

        order.addOrderDetails(orderDetail1);
        order.addOrderDetails(orderDetail2);

        Customer customer = new Customer();
        customer.setName("Vesa hekkinson");
        customer.setEmail("vesahekkinson@gmail.com");
        customer.setContact("9422581049");
        Customer save = customerRepository.save(customer);
        order.setCustomer(save);

        orderRepository.save(order);

        return Boolean.TRUE;
    }
}
