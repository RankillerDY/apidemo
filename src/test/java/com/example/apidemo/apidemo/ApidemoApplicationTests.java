package com.example.apidemo.apidemo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ApidemoApplicationTests {
    private final static Logger logger = LoggerFactory.getLogger(ApidemoApplicationTests.class);

    @Test
    void contextLoads() {
//        logger.info(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
    }

}
