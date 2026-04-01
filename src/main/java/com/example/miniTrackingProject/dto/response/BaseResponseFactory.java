package com.example.miniTrackingProject.dto.response;

import com.example.miniTrackingProject.common.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.util.Date;
public class  BaseResponseFactory{

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .timestamp(new Date())
                .build();
    }

    public static BaseResponse<Object> error(ErrorCode errorCode, String message, HttpServletRequest request) {
        return BaseResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .error(message)
                .timestamp(new Date())
                .build();
    }
}
