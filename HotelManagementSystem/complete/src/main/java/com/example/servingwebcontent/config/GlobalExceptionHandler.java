package com.example.servingwebcontent.config;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<?> handleSql(SQLException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("error", "Lỗi CSDL", "message", e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {
    return ResponseEntity.badRequest()
        .body(Map.of("error", "Dữ liệu không hợp lệ"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleOther(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("error", "Lỗi hệ thống", "message", e.getMessage()));
  }
}
