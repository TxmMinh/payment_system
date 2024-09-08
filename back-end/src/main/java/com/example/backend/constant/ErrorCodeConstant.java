package com.example.backend.constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.text.MessageFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCodeConstant {
    public static final String REQUIRED_VALIDATE = "error.required";
    public static final String INVALID_VALIDATE = "error.invalid";
    public static final String PAYPAL_ERROR = "error.paypal";
    public static final String NOT_FOUND = "error.{0}.not-found";

    public static String getErrorCode(String code, Object... args) {
        return MessageFormat.format(code, args);
    }
}
