package com.example.backend.service;

import com.example.backend.dto.request.PaymentExecuteRequestDto;
import com.example.backend.dto.request.PaymentRequestDto;
import com.example.backend.dto.response.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto createPayment(PaymentRequestDto paymentRequest);

    PaymentResponseDto executePayment(PaymentExecuteRequestDto paymentExecuteRequestDto);
}
