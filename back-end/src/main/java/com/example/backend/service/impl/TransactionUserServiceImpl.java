package com.example.backend.service.impl;

import com.example.backend.dto.response.TransactionUserResponseDto;
import com.example.backend.repository.TransactionUserRepository;
import com.example.backend.service.TransactionUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionUserServiceImpl implements TransactionUserService {
    private final TransactionUserRepository transactionUserRepository;

    @Override
    public Page<TransactionUserResponseDto> getAllTransactionUser(Pageable pageable) {
        return transactionUserRepository.getAllTransactionUser(pageable);
    }
}
