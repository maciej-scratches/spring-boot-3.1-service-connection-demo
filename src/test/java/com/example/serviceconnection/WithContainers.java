package com.example.serviceconnection;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcServiceConnection;

@Testcontainers
public interface WithContainers {

    @JdbcServiceConnection
    @Container
    PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:15.1"));
}
