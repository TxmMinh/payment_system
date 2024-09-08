package com.example.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {
    private String method;

    private BigDecimal amount;

    private String currency;

    private String intent;

    private String description;

    private String successUrl;

    private String cancelUrl;
}
