package com.example.serviceconnection;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcServiceConnection;

public abstract class IntegrationTest {

    @JdbcServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:15.1"));

    static {
        postgres.start();
    }
}
