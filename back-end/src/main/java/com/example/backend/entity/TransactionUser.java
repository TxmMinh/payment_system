package com.example.backend.entity;

import com.example.backend.enumeration.TransactionStatus;
import com.example.backend.enumeration.TypeCurrency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name="transaction_user")
public class TransactionUser extends SystemDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_status", columnDefinition = "varchar(10) default 'PENDING'")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TransactionStatus transactionStatus = TransactionStatus.PENDING;

    @Column(name = "transaction_id", columnDefinition = "varchar(255)", unique = true)
    private String transactionId;

    @Column(name = "currency", columnDefinition = "varchar(10) default 'USD'")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TypeCurrency typeCurrency = TypeCurrency.USD;

    @ManyToOne
    @JoinColumn(name = "fk_wallet_user_id")
    private WalletUser walletUser;

    @Column(name = "gross")
    private BigDecimal gross;

    @Column(name = "net")
    private BigDecimal net;

    @Column(name = "fee")
    private BigDecimal fee;

    @Column(name = "description", columnDefinition = "varchar(255)")
    private String description;

}
