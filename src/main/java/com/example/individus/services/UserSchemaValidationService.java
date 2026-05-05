package com.example.individus.services;

import com.networknt.schema.Error;
import com.networknt.schema.Schema;
import com.networknt.schema.SchemaRegistry;
import com.networknt.schema.SpecificationVersion;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Service
public class UserSchemaValidationService {
  private final ResourceLoader resourceLoader;
  private final ObjectMapper objectMapper;
  private Schema userSchema;

  public UserSchemaValidationService(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
    this.resourceLoader = resourceLoader;
    this.objectMapper = objectMapper;
  }

  @PostConstruct
  public void loadSchema() {
    Resource resource = resourceLoader.getResource("classpath:schemas/user.jschema");
    try {
      userSchema = SchemaRegistry
          .withDefaultDialect(SpecificationVersion.DRAFT_2020_12)
          .getSchema(resource.getInputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Error> validateUserPayload(JsonNode json) {
    return userSchema.validate(json);
  }
}
