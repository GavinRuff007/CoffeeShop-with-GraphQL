package org.example.coffeestore.config;

import org.h2.tools.Server;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;

public class H2TcpInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext context) {

    Environment env = context.getEnvironment();

    if (!Arrays.asList(env.getActiveProfiles()).contains("dev")) {
      return;
    }

    String port = env.getProperty("sandbox.tcp-port");
    String dbUrl = env.getProperty("sandbox.db-url");
    String username = env.getProperty("sandbox.username");
    String password = env.getProperty("sandbox.password");

    try {
      Server.createTcpServer(
              "-tcp",
              "-tcpAllowOthers",
              "-tcpPort",
              port
      ).start();

      try (Connection ignored =
                   DriverManager.getConnection(dbUrl, username, password)) {}

    } catch (Exception e) {
      throw new RuntimeException("Failed to start H2 TCP server", e);
    }
  }
}

