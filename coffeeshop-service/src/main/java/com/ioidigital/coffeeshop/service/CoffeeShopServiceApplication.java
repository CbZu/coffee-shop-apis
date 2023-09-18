package com.ioidigital.coffeeshop.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ioidigital")
public class CoffeeShopServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoffeeShopServiceApplication.class, args);
    }
}
