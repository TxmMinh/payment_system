package com.example.backend.dto.request;

import com.example.backend.constant.ErrorCodeConstant;
import com.example.backend.enumeration.TypeCurrency;
import com.example.backend.validator.EnumValue;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class PaymentRequestDto {
    @NotNull(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    private String method;

    @NotNull(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    private BigDecimal amount;

    @NotNull(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    @EnumValue(enumClass = TypeCurrency.class)
    private String currency;

    @Size(min = 1, max = 255, message = ErrorCodeConstant.REQUIRED_VALIDATE)
    private String description;
}
