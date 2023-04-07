package com.example;

import javax.sql.DataSource;

import com.example.contextinitializer.TestcontainersInitializer;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ContextConfiguration(initializers = TestcontainersInitializer.class)
class AppContextInitializerTests {

    @Test
    void contextLoads(@Autowired DataSource dataSource) {
    }
}
