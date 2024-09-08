package com.example.backend.repository;

import com.example.backend.entity.TransactionUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionUserRepository extends JpaRepository<TransactionUser, Integer> {
}
