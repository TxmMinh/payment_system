package com.example.backend.dto.request;

import com.example.backend.constant.ErrorCodeConstant;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentExecuteRequestDto {
    @NotNull(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    private String paymentId;

    @NotNull(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    private String payerId;
}
