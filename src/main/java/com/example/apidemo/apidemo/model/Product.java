package com.example.apidemo.apidemo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
  @Id
  //    @GeneratedValue(strategy = GenerationType.AUTO)
  private Long Product_id;

  @Column(name = "productName", nullable = false, unique = true, length = 300)
  private String productName;

  @Column(name = "product_year", nullable = false)
  private int product_year;

  @Column(name = "price", nullable = false)
  private Double price;

  //    @Transient
  //    private int age;
  //
  //    public int getAge() {
  //        return Calendar.getInstance().get(Calendar.YEAR) - product_year;
  //    }
  //
  //    public Product(String productName, int product_year, Double price) {
  //        this.productName = productName;
  //        this.product_year = product_year;
  //        this.price = price;
  //    }

  @Override
  public String toString() {
    return "Product{"
        + "Product_id="
        + Product_id
        + ", productName='"
        + productName
        + '\''
        + ", product_year="
        + product_year
        + ", price="
        + price
        + '}';
  }
}
