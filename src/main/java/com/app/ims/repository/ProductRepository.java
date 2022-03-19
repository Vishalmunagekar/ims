package com.app.ims.repository;

import com.app.ims.model.Product;
import com.app.ims.model.ProductType;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @ApiOperation(value = "Get products with code containing", notes = "")
    Page<Product> findByCodeContains(@Param("code") String code,Pageable pageable);

    @ApiOperation(value = "Get products with type", notes = "")
    Page<Product> findByType(@Param("type") ProductType type, Pageable pageable);

    @ApiOperation(value = "Get products by name", notes = "")
    Page<Product> findByNameContains(@Param("name") String name, Pageable pageable);
}
