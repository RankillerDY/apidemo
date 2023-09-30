package com.example.apidemo.apidemo.controller;

import static org.mockito.Mockito.when;

import com.example.apidemo.apidemo.model.Product;
import com.example.apidemo.apidemo.model.ResponseObject;
import com.example.apidemo.apidemo.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// This is an api controller
@WebMvcTest(controllers = Product_Controller_Tests.class)
@EnableWebMvc
@AutoConfigureMockMvc(addFilters = false) // filter false help circumvent Spring Security
@ExtendWith(MockitoExtension.class)
@Import(ProductController.class)
class Product_Controller_Tests {
  @Autowired
  private MockMvc mockMvc; // MockMvc help us easy to perform get, post, put, patch in out test

  @Autowired private ObjectMapper objectMapper;
  private Product product;

  @MockBean ProductRepository productRepository;

  @BeforeEach
  public void init() {
    product = new Product(1L, "Samsung S9", 2020, 3.2);
  }

  private Logger logger = LoggerFactory.getLogger(ProductController.class);

  @Test
  void Product_createProduct_ReturnSaved() throws Exception {
    ResponseObject responseDTO = new ResponseObject("You made it!!", "Successfully", product);
    when(productRepository.findByProductName(product.getProductName().trim()))
        .thenReturn(Collections.emptyList());
    when(productRepository.save(Mockito.any())).thenReturn(product);
    //    logger.info("Product information " + product.toString());
    ResultActions response =
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/Products/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));
    response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
  }
}
