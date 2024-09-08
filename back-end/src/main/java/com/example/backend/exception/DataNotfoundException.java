package com.example.backend.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class DataNotfoundException extends AbstractThrowableProblem {
    public DataNotfoundException(URI type, String messageCode) {
        super(type, messageCode, Status.NOT_FOUND, null, null, null, null);
    }

    public DataNotfoundException(String messageCode) {
        this(URI.create("data-notfound"), messageCode);
    }
}
