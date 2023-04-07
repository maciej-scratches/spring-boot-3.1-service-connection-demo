package com.example;

import java.util.List;

import com.example.serviceconnection.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.service.connection.CustomServiceConnectionContextCustomizerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

/**
 * this tests does not work with Spring Boot out of the box.
 * Support for service connection declarations is provided by {@link CustomServiceConnectionContextCustomizerFactory}.
 */
@SpringBootTest(classes = SpringBoot31DemoApplication.class)
@ContextConfiguration(classes = TestcontainersConfiguration.class)
class AppServiceConnectionCompositionTests {

    @Test
    void contextLoads(@Autowired JdbcTemplate jdbcTemplate) {
        List<Long> query = jdbcTemplate.query("select 1", (rs, rowNum) -> rs.getLong(1));
        System.out.println(query);
    }
}
