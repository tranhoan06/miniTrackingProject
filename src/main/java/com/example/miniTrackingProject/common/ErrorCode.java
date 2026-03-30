package com.example.miniTrackingProject.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // General Errors
    INTERNAL_ERROR(500, "Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR),

    // Authentication & Authorization
    UNAUTHORIZED(401, "Email or password incorrect", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, "Access denied", HttpStatus.FORBIDDEN),
    TOKEN_INVALID(401, "Invalid JWT token", HttpStatus.UNAUTHORIZED),

    // User Management
    USER_EXISTED(400, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(400, "User does not exist", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_FOUND(400, "Address does not exist", HttpStatus.BAD_REQUEST),
    USERNAME_IS_DUPLICATED(400, "Username is duplicated", HttpStatus.BAD_REQUEST),
    // Course Management
    COURSE_NOT_FOUND(404, "Course not found", HttpStatus.NOT_FOUND),
    LESSON_NOT_FOUND(404, "Lesson not found", HttpStatus.NOT_FOUND),

    DATA_INTEGRITY_VIOLATION(409, "Data integrity violation", HttpStatus.CONFLICT);

    private final int code;
    private final String message;
    private final HttpStatus status;
}
