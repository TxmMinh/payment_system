package com.example.backend.dto.response;

import com.example.backend.enumeration.TransactionStatus;
import com.example.backend.enumeration.TypeCurrency;
import java.math.BigDecimal;
import java.time.Instant;

public interface TransactionUserResponseDto {
    Long getId();

    TransactionStatus getTransactionStatus();

    String getTransactionId();

    TypeCurrency getTypeCurrency();

    BigDecimal getGross();

    BigDecimal getNet();

    BigDecimal getFee();

    String getDescription();

    Instant getCreatedDate();
}
