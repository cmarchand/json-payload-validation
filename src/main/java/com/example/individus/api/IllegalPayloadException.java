package com.example.individus.api;

import com.networknt.schema.Error;

import java.util.List;

public class IllegalPayloadException extends RuntimeException {
  private final List<Error> errors;

  public IllegalPayloadException(List<Error> errors) {
    this.errors = errors;
  }

  public List<Error> getErrors() {
    return errors;
  }
}
