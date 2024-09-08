package com.example.backend.service.impl;

import com.example.backend.constant.ErrorCodeConstant;
import com.example.backend.dto.PaymentDto;
import com.example.backend.dto.request.PaymentExecuteRequestDto;
import com.example.backend.dto.request.PaymentRequestDto;
import com.example.backend.dto.response.PaymentResponseDto;
import com.example.backend.exception.PayPalRestException;
import com.example.backend.mapper.PayPalMapper;
import com.example.backend.service.PaymentService;
import com.example.backend.service.PaymentTransactionService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Value("${application.paypal.execute-url}")
    private String successUrl;

    @Value("${application.paypal.cancel-url}")
    private String cancelUrl;

    @Value("${application.paypal.email}")
    private String paypalEmail;

    private final APIContext apiContext;

    private final PaymentTransactionService paymentTransactionService;

    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequest) {
        PaymentResponseDto response = new PaymentResponseDto();
        PaymentDto paymentDto = getPaymentDto(paymentRequest);
        PayPalMapper payPalMapper = new PayPalMapper(paypalEmail);
        Payment payment = payPalMapper.toPayment(paymentDto);
        try {
            Payment createdPayment = payment.create(apiContext);
            if (createdPayment != null) {
                List<Links> links = createdPayment.getLinks();
                String redirectUrl = links.stream().filter(link -> link.getRel().equals("approval_url")).findFirst().map(Links::getHref).orElse(null);
                response.setStatus("success");
                response.setRedirectUrl(redirectUrl);
                return response;
            } else {
                throw new PayPalRestException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.PAYPAL_ERROR));
            }
        } catch (PayPalRESTException e) {
            log.error("Error happened during payment creation: ", e);
            throw new PayPalRestException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.PAYPAL_ERROR));
        }
    }

    @Override
    public PaymentResponseDto executePayment(PaymentExecuteRequestDto paymentExecuteRequestDto) {
        PaymentResponseDto response = new PaymentResponseDto();
        Payment payment = new Payment();
        payment.setId(paymentExecuteRequestDto.getPaymentId());
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(paymentExecuteRequestDto.getPayerId());
        try {
            Payment createdPayment = payment.execute(apiContext, paymentExecution);
            paymentTransactionService.createTransactionDetails(paymentExecuteRequestDto.getPaymentId());
            if (createdPayment.getState().equals("approved")) {
                response.setStatus("success");
                return response;
            }
            throw new PayPalRestException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.PAYPAL_ERROR));
        } catch (PayPalRESTException e) {
            log.error("Error happened during payment execute:: ", e);
            throw new PayPalRestException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.PAYPAL_ERROR));
        }
    }

    private PaymentDto getPaymentDto(PaymentRequestDto paymentRequest) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setMethod(paymentRequest.getMethod());
        paymentDto.setAmount(paymentRequest.getAmount());
        paymentDto.setCurrency(paymentRequest.getCurrency());
        paymentDto.setDescription(paymentRequest.getDescription());
        paymentDto.setIntent("sale");
        paymentDto.setCancelUrl(cancelUrl);
        paymentDto.setSuccessUrl(successUrl);
        return paymentDto;
    }

}
