package com.example.backend.service.impl;

import com.example.backend.constant.ErrorCodeConstant;
import com.example.backend.entity.TransactionUser;
import com.example.backend.entity.WalletUser;
import com.example.backend.enumeration.TransactionStatus;
import com.example.backend.enumeration.TypeCurrency;
import com.example.backend.exception.DataNotfoundException;
import com.example.backend.exception.PayPalRestException;
import com.example.backend.repository.TransactionUserRepository;
import com.example.backend.repository.WalletUserRepository;
import com.example.backend.service.PaymentTransactionService;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RelatedResources;
import com.paypal.api.payments.Sale;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {
    private final WalletUserRepository walletUserRepository;
    private final TransactionUserRepository transactionUserRepository;
    private final APIContext apiContext;
    private static final Long SYSTEM_ID = 1L;

    @Override
    @Transactional
    public void createTransactionDetails(String transactionId) {
        // create transaction system
        WalletUser walletUser = walletUserRepository.findByIdWithLock(SYSTEM_ID)
                .orElseThrow(() -> new DataNotfoundException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.NOT_FOUND, "system-wallet")));
        // Fetch payment details from PayPal
        Payment payment = this.getTransactionSystem(transactionId);
        TransactionUser transactionUser = this.createTransactionUser(walletUser, payment);

        if (payment.getState().equals("approved")) {
            // increase balance in wallet system
            this.addBalanceToWalletUser(walletUser, transactionUser);
            transactionUserRepository.save(transactionUser);
            walletUserRepository.save(walletUser);
        } else {
            log.error("Error happened during save payment");
            throw new PayPalRestException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.PAYPAL_ERROR));
        }
    }

    private Payment getTransactionSystem(String transactionId) {
        try {
            return Payment.get(apiContext, transactionId);
        } catch (PayPalRESTException e) {
            throw new PayPalRestException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.PAYPAL_ERROR));
        }
    }

    private TransactionUser createTransactionUser(WalletUser walletUser, Payment payment) {
        TransactionUser transactionUser = this.transactionSystemMapper(walletUser, payment);
        if (payment.getState().equals("approved")) {
            transactionUser.setTransactionStatus(TransactionStatus.COMPLETED);
        }
        this.calculateFeesAndNet(payment, transactionUser);
        return transactionUser;
    }

    private TransactionUser transactionSystemMapper(WalletUser walletUser, Payment payment) {
        TransactionUser transactionUser = new TransactionUser();
        transactionUser.setWalletUser(walletUser);
        transactionUser.setTransactionId(payment.getId());
        transactionUser.setLastModifiedDate(LocalDateTime.parse(payment.getCreateTime(), DateTimeFormatter.ISO_DATE_TIME).atZone(ZoneId.systemDefault()).toInstant());
        transactionUser.setDescription(payment.getTransactions().get(0).getDescription());
        transactionUser.setTypeCurrency(TypeCurrency.USD);
        transactionUser.setGross(new BigDecimal(payment.getTransactions().get(0).getAmount().getTotal()));
        return transactionUser;
    }

    private void calculateFeesAndNet(Payment payment, TransactionUser transactionUser) {
        BigDecimal fee = BigDecimal.ZERO;
        Optional<Sale> saleOptional = payment.getTransactions().stream().flatMap(transaction -> transaction.getRelatedResources().stream()).map(RelatedResources::getSale).filter(Objects::nonNull).findFirst();
        if (saleOptional.isPresent()) {
            Sale sale = saleOptional.get();
            fee = sale.getTransactionFee().getValue() != null ? new BigDecimal(sale.getTransactionFee().getValue()) : BigDecimal.ZERO;
        }
        BigDecimal net = transactionUser.getGross().subtract(fee);
        transactionUser.setFee(fee);
        transactionUser.setNet(net);
    }

    private void addBalanceToWalletUser(WalletUser walletUser, TransactionUser transactionUser) {
        BigDecimal balanceSystem = walletUser.getBalance();
        walletUser.setBalance(balanceSystem.add(transactionUser.getNet()));
    }
}
