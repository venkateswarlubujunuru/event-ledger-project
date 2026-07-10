package com.accountservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accountservice.dto.AccountResponse;
import com.accountservice.dto.BalanceResponse;
import com.accountservice.dto.TransactionRequest;
import com.accountservice.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Process Credit/Debit Transaction
     */
    @PostMapping("/transactions")
    public ResponseEntity<AccountResponse> processTransaction(
            @Valid @RequestBody TransactionRequest request) {

        AccountResponse response = accountService.processTransaction(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get Account Details
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(
            @PathVariable String accountId) {

        return ResponseEntity.ok(accountService.getAccount(accountId));
    }

    /**
     * Get Account Balance
     */
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(
            @PathVariable String accountId) {

        return ResponseEntity.ok(accountService.getBalance(accountId));
    }

}