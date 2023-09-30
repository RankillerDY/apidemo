    package com.example.apidemo.apidemo.repositories;

import com.example.apidemo.apidemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductName(String productName);

}
