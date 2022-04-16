package com.app.ims.service;

import com.app.ims.controller.UserController;
import com.app.ims.dto.CreateProductRequest;
import com.app.ims.dto.UpdateProductRequest;
import com.app.ims.model.Product;
import com.app.ims.model.ProductType;
import com.app.ims.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long id){
        return productRepository.findById(id).get();
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Page<Product> getProductByCode(String code){
        return productRepository.findByCodeContains(code, PageRequest.ofSize(10));
    }

    public Page<Product> getProductByType(ProductType type){
        return productRepository.findByType(type, PageRequest.ofSize(10));
    }

    public Page<Product> getProductByName(String name){
        return productRepository.findByNameContains(name, PageRequest.ofSize(10));
    }

    public Product createProduct(CreateProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setCode(productRequest.getCode());
        product.setType(productRequest.getType());
        product.setPrice(productRequest.getPrice());
        return productRepository.save(product);
    }

    public Product updateProductById(Long id, UpdateProductRequest productRequest){
        Product optionalProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id : " + id));
        optionalProduct.setName(productRequest.getName());
        optionalProduct.setType(productRequest.getType());
        optionalProduct.setPrice(productRequest.getPrice());
        optionalProduct.setCode(productRequest.getCode());
        return productRepository.save(optionalProduct);
    }

    public Boolean deleteProductById(Long id){
        productRepository.deleteById(id);
        return Boolean.TRUE;
    }
}
