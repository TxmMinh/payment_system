package com.example.backend.repository;

import com.example.backend.dto.response.WallerUserResponseDto;
import com.example.backend.entity.WalletUser;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletUserRepository extends JpaRepository<WalletUser, Long>  {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM WalletUser w WHERE w.id = :walletId")
    Optional<WalletUser> findByIdWithLock(@Param("walletId") Long walletId);

    @Query("SELECT w.id as id, w.createdDate as createdDate, w.typeCurrency as typeCurrency, w.balance as balance FROM WalletUser w WHERE w.id = :walletId")
    Optional<WallerUserResponseDto> getWalletUser(@Param("walletId") Long walletId);

}
