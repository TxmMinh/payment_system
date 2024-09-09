package com.example.backend.controller;

import com.example.backend.dto.response.TransactionUserResponseDto;
import com.example.backend.service.TransactionUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionUserController {
    private final TransactionUserService transactionUserService;

    @Operation(summary = "API for displays all favorite provider by consumer")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List favorite provider"),
            @ApiResponse(responseCode = "400", description = "List favorite provider not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @GetMapping("/user")
    public ResponseEntity<Page<TransactionUserResponseDto>> getAllTransactionUser(@PageableDefault(page = 0, size = 8) Pageable pageable) {
        return new ResponseEntity<>(transactionUserService.getAllTransactionUser(pageable), HttpStatus.OK);
    }
}
