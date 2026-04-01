package com.example.miniTrackingProject.dto.response;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> implements Serializable {

    private int code;
    private String message;
    private T data;
    private Object error;
    private Date timestamp;
}
