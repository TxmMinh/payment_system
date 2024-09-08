package com.example.backend.controller;

import com.example.backend.dto.request.PaymentExecuteRequestDto;
import com.example.backend.dto.request.PaymentRequestDto;
import com.example.backend.dto.response.PaymentResponseDto;
import com.example.backend.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "API for payment")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "create payment success"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody PaymentRequestDto paymentRequest) {
        return ResponseEntity.ok(paymentService.createPayment(paymentRequest));
    }

    @Operation(summary = "API for execute payment")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "execute payment"), @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping("/execute")
    public ResponseEntity<PaymentResponseDto> executePayment(@RequestBody PaymentExecuteRequestDto paymentExecuteRequestDto) {
        return ResponseEntity.ok(paymentService.executePayment(paymentExecuteRequestDto));
    }
}
