package com.example.apidemo.apidemo.controller;

import com.example.apidemo.apidemo.model.Product;
import com.example.apidemo.apidemo.model.ResponseObject;
import com.example.apidemo.apidemo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {
  @Autowired private ProductRepository productRepository;

  @GetMapping
  public List<Product> ProductList() {
    return productRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseObject> findByID(@PathVariable Long id) {
    Optional<Product> p1 = productRepository.findById(id);
    // Khi response ve object ở restController
    // thì nó sẽ chuyển object đó thành json
    if (p1.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseObject("Query product success", "ok", p1));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ResponseObject("Cannot find product with id " + id, "false", ""));
    }
  }

  @PostMapping("/insert")
  public ResponseEntity<ResponseObject> createProduct(@RequestBody Product product) {
    List<Product> foundProducts =
        productRepository.findByProductName(product.getProductName().trim());
    if (foundProducts.size() > 0) {
      return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
          .body(new ResponseObject("Product Name Already Taken", "failed", ""));
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseObject("Added Successful", "ok", productRepository.save(product)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseObject> updateProduct(
      @PathVariable Long id, @RequestBody Product product) {
    Product product1 =
        productRepository
            .findById(id)
            .map(
                p1 -> {
                  p1.setProductName(product.getProductName());
                  p1.setProduct_year(product.getProduct_year());
                  p1.setPrice(product.getPrice());
                  return productRepository.save(p1);
                })
            .orElseGet(
                () -> {
                  product.setProduct_id(id);
                  return productRepository.save(product);
                });
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseObject("Updated Successful", "ok", product1));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
    boolean exist = productRepository.existsById(id);
    if (exist) {
      productRepository.deleteById(id);
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Deleted!!!", "ok", ""));
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseObject("Could not find the product", "failed", ""));
  }
}
