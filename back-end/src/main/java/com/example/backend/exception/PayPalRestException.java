package com.example.backend.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class PayPalRestException extends AbstractThrowableProblem {

    public PayPalRestException(URI type, String messageCode) {
        super(type, messageCode, Status.INTERNAL_SERVER_ERROR, null, null, null, null);
    }

    public PayPalRestException(String messageCode) {
        this(URI.create("paypal-exception"), messageCode);
    }
}
