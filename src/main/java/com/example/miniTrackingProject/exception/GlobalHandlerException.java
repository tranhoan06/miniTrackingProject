package com.example.miniTrackingProject.exception;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestControllerAdvice
@Slf4j(topic = "GLOBAL-EXCEPTION")
public class GlobalHandlerException {

    // 1. Xử lý Custom Exception
    @ExceptionHandler(JavaBuilderException.class)
    public ResponseEntity<BaseResponse> handleJavaBuilderException(
            JavaBuilderException exception, WebRequest request) {

        BaseResponse response = BaseResponse.builder()
                .code(exception.getErrorCode().getCode())
                .error(exception.getErrorCode().getStatus().getReasonPhrase())
                .message(exception.getMessage())
                .timestamp(new Date())
                .build();

        return ResponseEntity
                .status(exception.getErrorCode().getStatus())
                .body(response);
    }

    // 2. Xử lý Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidationException(
            MethodArgumentNotValidException e, WebRequest request) {

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // Lấy tất cả error messages
//        List<String> errors = fieldErrors.stream()
//                .map(FieldError::getDefaultMessage) // lấy từng FieldError để gọi getDefaultMessage
//                .toList();

        List<String> errors = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .toList();

        BaseResponse<Object> response = BaseResponse.builder()
                .timestamp(new Date())
                .code(HttpStatus.BAD_REQUEST.value())
                .message(errors.size() > 1 ? errors.toString() : errors.get(0))
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // 3. Xử lý Authentication Errors
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<BaseResponse> handleBadCredentialsException(
//            BadCredentialsException exception, WebRequest request) {
//
//        log.error("Authentication failed: {}", exception.getMessage());
//        BaseResponse response = buildErrorCodeResponse(ErrorCode.UNAUTHORIZED, request);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//    }

    // 4. Xử lý Missing Headers/Cookies
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<BaseResponse> handleMissingRequestHeaderException(
            MissingRequestHeaderException exception, WebRequest request) {

        BaseResponse response = BaseResponse.builder()
                .timestamp(new Date())
                .code(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Required header '" + exception.getHeaderName() + "' is missing")
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // 5. Xử lý Database Constraint Violations
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException exception, WebRequest request) {

        BaseResponse response = buildErrorCodeResponse(
                ErrorCode.DATA_INTEGRITY_VIOLATION, request);
        return ResponseEntity.badRequest().body(response);
    }

    // 6. Catch-all cho các exceptions không được xử lý
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleAllExceptions(
            Exception ex, WebRequest request) {

        log.error("Unexpected exception occurred: ", ex);
        BaseResponse response = buildErrorCodeResponse(ErrorCode.INTERNAL_ERROR, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // Helper method để build BaseResponse từ ErrorCode
    private BaseResponse buildErrorCodeResponse(ErrorCode errorCode, WebRequest request) {
        return BaseResponse.builder()
                .timestamp(new Date())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .error(errorCode.getStatus().getReasonPhrase())
                .build();
    }
}
