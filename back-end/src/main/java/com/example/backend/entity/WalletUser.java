package com.example.backend.entity;

import com.example.backend.enumeration.TypeCurrency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Entity
@Data
@Table(name="wallet_user")
@NoArgsConstructor
public class WalletUser extends SystemDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency", columnDefinition = "varchar(10) default 'USD'")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TypeCurrency typeCurrency = TypeCurrency.USD;

    @Column(name = "balance")
    private BigDecimal balance;

    @OneToMany(mappedBy = "walletUser", fetch = FetchType.LAZY)
    private List<TransactionUser> transactionUsers = new ArrayList<>();
}
