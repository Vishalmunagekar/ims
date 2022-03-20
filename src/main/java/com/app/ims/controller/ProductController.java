package com.app.ims.controller;


import com.app.ims.model.Product;
import com.app.ims.model.ProductType;
import com.app.ims.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "api/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return new ResponseEntity<Product>(productRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<List<Product>>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/code/{code}")
    public ResponseEntity<Page<Product>> getProductByCode(@PathVariable String code){
        return new ResponseEntity<>(productRepository.findByCodeContains(code, PageRequest.ofSize(5)), HttpStatus.OK);
    }

    @GetMapping(value = "/type/{type}")
    public ResponseEntity<Page<Product>> getProductByType(@PathVariable ProductType type){
        return new ResponseEntity<>(productRepository.findByType(type, PageRequest.ofSize(5)), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<Page<Product>> getProductByName(@PathVariable String name){
        return new ResponseEntity<>(productRepository.findByNameContains(name, PageRequest.ofSize(5)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable Long id, @RequestBody Product product){
        Product optionalProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id : " + id));
        optionalProduct.setName(product.getName());
        optionalProduct.setType(product.getType());
        optionalProduct.setTotalCost(product.getTotalCost());
        optionalProduct.setCode(product.getCode());
        return new ResponseEntity<>(productRepository.save(optionalProduct), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id){
        productRepository.deleteById(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
