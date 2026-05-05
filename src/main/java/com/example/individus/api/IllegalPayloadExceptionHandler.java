package com.example.individus.api;

import com.networknt.schema.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class IllegalPayloadExceptionHandler {

  @ExceptionHandler(IllegalPayloadException.class)
  public ResponseEntity<Map<String,Object>> handleIllegalPayload(IllegalPayloadException e) {
    Set<String> messages = e.getErrors()
        .stream()
        .map(IllegalPayloadExceptionHandler::format)
        .collect(Collectors.toSet());
    Map<String,Object> body = Map.of(
        "status", HttpStatus.BAD_REQUEST.value(),
        "message", "payload does not respect schema",
        "errors", messages
    );
    return ResponseEntity.badRequest().body(body);
  }

  private static String format(Error error) {
    return error.getInstanceLocation().toString() + ": " +
        error.getMessage() + " (" + error.getDetails() + ")";
  }
}
