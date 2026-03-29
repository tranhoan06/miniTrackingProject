package com.example.miniTrackingProject.exception;

import com.example.miniTrackingProject.common.ErrorCode;
import lombok.Getter;

@Getter
public class JavaBuilderException extends RuntimeException {

    private final ErrorCode errorCode;

    public JavaBuilderException(ErrorCode errorCode) {
        // super = gọi constructor của class cha RuntimeException sau đó gán message cho exception cha
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
