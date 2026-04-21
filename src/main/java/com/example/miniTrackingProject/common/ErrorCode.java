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
    INVALID_STATUS(403, "Invalid status", HttpStatus.FORBIDDEN),
    TOKEN_INVALID(401, "Invalid JWT token", HttpStatus.UNAUTHORIZED),

    // User Management
    USER_EXISTED(400, "User already exists", HttpStatus.BAD_REQUEST),
    NOT_FOUND(400, "Error not found", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(400, "User does not exist", HttpStatus.BAD_REQUEST),
    CART_NOT_FOUND(400, "Cart does not exist", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(400, "Product does not exist", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_FOUND(400, "Address does not exist", HttpStatus.BAD_REQUEST),
    USERNAME_IS_DUPLICATED(400, "Username is duplicated", HttpStatus.BAD_REQUEST),
    CATEGORY_IS_DUPLICATED(400, "Category is duplicated", HttpStatus.BAD_REQUEST),
    CATEGORYID_NOT_FOUND(400, "Category does not exist", HttpStatus.BAD_REQUEST),
    VOUCHER_NOT_FOUND(400, "Voucher does not exist", HttpStatus.BAD_REQUEST),
    VOUCHER_EXPIRES(400, "Voucher expires", HttpStatus.BAD_REQUEST),
    VOUCHER_HAS_NOT_STARTED_YET(400, "Voucher has not started yet", HttpStatus.BAD_REQUEST),
    REVIEW_ALREADY_EXISTS(400, "Can't be evaluated", HttpStatus.BAD_REQUEST),
    // Course Management
    COURSE_NOT_FOUND(404, "Course not found", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_ENOUGH_STOCK(404, "Product not enough stock", HttpStatus.NOT_FOUND),

    LESSON_NOT_FOUND(404, "Lesson not found", HttpStatus.NOT_FOUND),
    INVENTORY_HAS_RESERVED_QUANTITY(400, "Cannot update inventory while orders are being processed", HttpStatus.BAD_REQUEST),
    SOME_ORDERS_NOT_FOUND_OR_INVALID(404, "Some orders were not found or are invalid\n", HttpStatus.NOT_FOUND),
    DATA_INTEGRITY_VIOLATION(409, "Data integrity violation", HttpStatus.CONFLICT),

    ORDER_BELOW_MIN_AMOUNT(400, "Order does not meet minimum amount for voucher", HttpStatus.BAD_REQUEST),
    SHIPPING_PROVIDER_NOT_FOUND(400, "No shipping provider available", HttpStatus.BAD_REQUEST);


    private final int code;
    private final String message;
    private final HttpStatus status;
}
