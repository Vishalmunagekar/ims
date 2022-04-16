package com.app.ims.controller;


import com.app.ims.dto.CreateProductRequest;
import com.app.ims.dto.UpdateProductRequest;
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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "api/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProductById(@NotNull @PathVariable Long id){
        return new ResponseEntity<Product>(productRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<List<Product>>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/code/{code}")
    public ResponseEntity<Page<Product>> getProductByCode(@NotBlank @PathVariable String code){
        return new ResponseEntity<>(productRepository.findByCodeContains(code, PageRequest.ofSize(10)), HttpStatus.OK);
    }

    @GetMapping(value = "/type/{type}")
    public ResponseEntity<Page<Product>> getProductByType(@NotBlank @PathVariable ProductType type){
        return new ResponseEntity<>(productRepository.findByType(type, PageRequest.ofSize(10)), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<Page<Product>> getProductByName(@NotBlank @PathVariable String name){
        return new ResponseEntity<>(productRepository.findByNameContains(name, PageRequest.ofSize(10)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setCode(productRequest.getCode());
        product.setType(productRequest.getType());
        product.setPrice(productRequest.getPrice());
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateProductById(@NotNull @PathVariable Long id, @Valid @RequestBody UpdateProductRequest productRequest){
        Product optionalProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id : " + id));
        optionalProduct.setName(productRequest.getName());
        optionalProduct.setType(productRequest.getType());
        optionalProduct.setPrice(productRequest.getPrice());
        optionalProduct.setCode(productRequest.getCode());
        return new ResponseEntity<>(productRepository.save(optionalProduct), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProductById(@NotNull @PathVariable Long id){
        productRepository.deleteById(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
