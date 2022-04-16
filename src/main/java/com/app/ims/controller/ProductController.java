package com.app.ims.controller;


import com.app.ims.dto.CreateProductRequest;
import com.app.ims.dto.UpdateProductRequest;
import com.app.ims.model.Product;
import com.app.ims.model.ProductType;
import com.app.ims.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "api/product")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProductById(@NotNull @PathVariable Long id){
        LOGGER.debug("getProductById id : {}", id);
        return new ResponseEntity<Product>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<List<Product>>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping(value = "/code/{code}")
    public ResponseEntity<Page<Product>> getProductByCode(@NotBlank @PathVariable String code){
        LOGGER.debug("getProductByCode code : {}", code);
        return new ResponseEntity<>(productService.getProductByCode(code), HttpStatus.OK);
    }

    @GetMapping(value = "/type/{type}")
    public ResponseEntity<Page<Product>> getProductByType(@NotBlank @PathVariable ProductType type){
        LOGGER.debug("getProductByType type : {}", type);
        return new ResponseEntity<>(productService.getProductByType(type), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<Page<Product>> getProductByName(@NotBlank @PathVariable String name){
        LOGGER.debug("getProductByName name : {}", name);
        return new ResponseEntity<>(productService.getProductByName(name), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@Valid @RequestBody CreateProductRequest productRequest){
        LOGGER.debug("createProduct Product : {}", productRequest.toString());
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateProductById(@NotNull @PathVariable Long id, @Valid @RequestBody UpdateProductRequest productRequest){
        LOGGER.debug("updateProductById id : {} and productRequest : {}", id, productRequest.toString());
        return new ResponseEntity<>(productService.updateProductById(id,productRequest), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProductById(@NotNull @PathVariable Long id){
        LOGGER.debug("deleteProductById id : {}", id);
        productService.deleteProductById(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
