package com.example.individus.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {
  RestTestClient client;

  @LocalServerPort
  int port;

  @DisplayName("Given a valid user, response should be 201")
  @Test
  public void test_valid() {
    // Given
    String json = """
        {
          "id": 1,
          "username": "Christophe Marchand",
          "email": "christophe@marchand.top",
          "age": 53
        }
        """;
    // When
    RestTestClient.ResponseSpec exchange = client
        .post()
        .uri("http://localhost:"+port+"/api/users")
        .body(json)
        .exchange();
    // Then
    exchange.expectStatus().isCreated();
    exchange.expectHeader().location("/api/users/1");
    exchange.expectBody(String.class).isEqualTo("{\"id\":1,\"username\":\"CHRISTOPHE MARCHAND\",\"email\":\"christophe@marchand.top\",\"age\":53}");
  }

  @DisplayName("Given a user without email, response should be 400")
  @Test
  public void test_invalid() {
    // Given
    String json = """
        {
          "id": 1,
          "username": "Christophe Marchand",
          "age": 53
        }
        """;
    // When
    RestTestClient.ResponseSpec exchange = client
        .post()
        .uri("http://localhost:"+port+"/api/users")
        .body(json)
        .exchange();
    // Then
    exchange.expectStatus().isBadRequest();
  }

  @BeforeEach
  void initClient() {
    client = RestTestClient.bindToServer()
        .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}
