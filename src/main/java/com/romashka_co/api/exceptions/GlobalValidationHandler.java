package com.romashka_co.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;

// Класс-обработчик исключений
@RestControllerAdvice
public class GlobalValidationHandler {
    // Обработка исключения при получении товара
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<HashMap<String, String>> handleProductNotFound(ProductNotFoundException ex) {
        HashMap<String, String> errorResponse = new HashMap<>();

        errorResponse.put("Ошибка", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Обработка исключения при создании или изменении товаров
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, String>> handleProductCreationErrors(MethodArgumentNotValidException ex) {
        HashMap<String, String> errorResponse = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
