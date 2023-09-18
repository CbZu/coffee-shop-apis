package com.ioidigital.order.service.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.ioidigital.order.service.dataaccess", "com.ioidigital.dataaccess" })
@EntityScan(basePackages = { "com.ioidigital.order.service.dataaccess", "com.ioidigital.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.ioidigital")
public class OrderServiceApplication {
    public static void main(String[] args) {
      SpringApplication.run(OrderServiceApplication.class, args);
    }
}
