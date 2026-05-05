package com.example.individus.api;

import com.example.individus.dto.User;
import com.example.individus.services.UserSchemaValidationService;
import com.networknt.schema.Error;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserSchemaValidationService validationService;
  private final ObjectMapper objectMapper;

  public UserController(UserSchemaValidationService validationService, ObjectMapper objectMapper) {
    this.validationService = validationService;
    this.objectMapper = objectMapper;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> createUser(@RequestBody JsonNode json) {
    List<Error> errors = validationService.validateUserPayload(json);
    if(errors.isEmpty()) {
      // TODO : check if ObjectReader is threadsafe
      ObjectReader objectReader = objectMapper.readerFor(User.class);
      User user = objectReader.readValue(json);
      return createUser(user);
    } else {
      throw new IllegalPayloadException(errors);
    }
  }

  private ResponseEntity<User> createUser(User user) {
    user.setUsername(user.getUsername().toUpperCase());
    try {
      return ResponseEntity
          .created(new URI("/api/users/"+user.getId()))
          .body(user);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
