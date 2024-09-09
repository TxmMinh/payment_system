package com.example.backend.service;

import com.example.backend.dto.response.TransactionUserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionUserService {
    Page<TransactionUserResponseDto> getAllTransactionUser(Pageable pageable);
}
