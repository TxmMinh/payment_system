package com.example.backend.dto.response;

import com.example.backend.enumeration.TypeCurrency;

import java.math.BigDecimal;
import java.time.Instant;

public interface WallerUserResponseDto {
    Long getId();

    TypeCurrency getTypeCurrency();

    BigDecimal getBalance();

    Instant getCreatedDate();
}
