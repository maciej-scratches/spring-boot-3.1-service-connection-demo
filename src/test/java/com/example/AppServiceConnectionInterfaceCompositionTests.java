package com.example;

import com.example.serviceconnection.WithContainers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

@Disabled("there is no support for interface based composition")
@SpringBootTest
class AppServiceConnectionInterfaceCompositionTests implements WithContainers {

    @Test
    void contextLoads() {
    }
}
