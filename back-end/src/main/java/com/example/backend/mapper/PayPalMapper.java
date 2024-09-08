package com.example.backend.mapper;


import com.example.backend.dto.PaymentDto;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payee;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PayPalMapper {
    private final String paypalEmail;

    public PayPalMapper(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    public Payment toPayment(PaymentDto paymentDto) {
        Amount amount = new Amount();
        amount.setCurrency(paymentDto.getCurrency());
        amount.setTotal(String.format(Locale.forLanguageTag(paymentDto.getCurrency()), "%.2f", paymentDto.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setDescription(paymentDto.getDescription());
        transaction.setAmount(amount);
        transaction.setPayee(new Payee().setEmail(paypalEmail));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(paymentDto.getMethod());

        Payment payment = new Payment();
        payment.setIntent(paymentDto.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(paymentDto.getCancelUrl());
        redirectUrls.setReturnUrl(paymentDto.getSuccessUrl());
        payment.setRedirectUrls(redirectUrls);

        return payment;
    }
}