package com.example.backend.service.impl;

import com.example.backend.constant.ErrorCodeConstant;
import com.example.backend.dto.response.WallerUserResponseDto;
import com.example.backend.exception.DataNotfoundException;
import com.example.backend.repository.WalletUserRepository;
import com.example.backend.service.WalletUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletUserServiceImpl implements WalletUserService {

    private final WalletUserRepository walletUserRepository;

    @Override
    public WallerUserResponseDto getWalletUser() {
        return walletUserRepository.getWalletUser(1L)
                .orElseThrow(() -> new DataNotfoundException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.NOT_FOUND, "user-wallet")));
    }
}
