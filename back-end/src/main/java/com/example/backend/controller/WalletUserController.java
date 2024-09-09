package com.example.backend.controller;

import com.example.backend.dto.response.WallerUserResponseDto;
import com.example.backend.service.WalletUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletUserController {
    private final WalletUserService walletUserService;

    @Operation(summary = "API for get wallet user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get wallet user success"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/user")
    public ResponseEntity<WallerUserResponseDto> createPayment() {
        return ResponseEntity.ok(walletUserService.getWalletUser());
    }
}
