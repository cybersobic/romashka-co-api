package com.romashka_co.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalValidationHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<HashMap<String, String>> handleProductNotFound(ProductNotFoundException ex) {
        HashMap<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Ошибка", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
