package com.example.backend.dto.response;

import lombok.Data;

@Data
public class PaymentResponseDto {
    private String status;
    private String redirectUrl;
}
