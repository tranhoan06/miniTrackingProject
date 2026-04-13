package com.example.miniTrackingProject.dto.request;

import com.example.miniTrackingProject.common.PayMethodEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest implements Serializable {

    @NotNull
    private List<Long> orderIds;

    private PayMethodEnum payMethod;
}
