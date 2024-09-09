package com.example.backend.repository;

import com.example.backend.dto.response.TransactionUserResponseDto;
import com.example.backend.entity.TransactionUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionUserRepository extends JpaRepository<TransactionUser, Integer> {
    @Query("SELECT t.id as id, t.typeCurrency as typeCurrency, t.transactionStatus as transactionStatus, t.transactionId as transactionId, " +
            " t.gross as gross, t.net as net, t.fee as fee, t.description as description, t.createdDate as createdDate " +
            "FROM TransactionUser t")
    Page<TransactionUserResponseDto> getAllTransactionUser(Pageable pageable);
}
