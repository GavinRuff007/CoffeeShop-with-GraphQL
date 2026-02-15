package org.example.coffeestore;

import org.example.coffeestore.config.H2TcpInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CoffeeStoreApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CoffeeStoreApplication.class);
        app.addInitializers(new H2TcpInitializer());
        app.run(args);
    }
}
