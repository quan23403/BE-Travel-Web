package com.example.Travel_web.BE.error;

import com.example.Travel_web.BE.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice  // Đánh dấu lớp này là Global Exception Handler
public class GlobalExceptionHandler {

    // Bắt lỗi MethodArgumentNotValidException (lỗi validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorMessages = new HashMap<>();

        // Lấy tất cả các lỗi từ BindingResult
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessages.put(fieldName, message);  // Thêm thông báo lỗi vào map
        });

        // Tạo ApiResponse với status "FAIL" và thông báo lỗi
        ApiResponse errorResponse = new ApiResponse("FAIL", "Lỗi validation", errorMessages);

        // Trả về thông báo lỗi dưới dạng ApiResponse với status 400 (Bad Request)
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
