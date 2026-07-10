package com.accountservice.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accountservice.dto.AccountResponse;
import com.accountservice.dto.BalanceResponse;
import com.accountservice.dto.TransactionRequest;
import com.accountservice.entity.Account;
import com.accountservice.entity.Transaction;
import com.accountservice.entity.TransactionType;
import com.accountservice.exception.AccountNotFoundException;
import com.accountservice.exception.DuplicateTransactionException;
import com.accountservice.exception.InsufficientBalanceException;
import com.accountservice.mapper.AccountMapper;
import com.accountservice.repository.AccountRepository;
import com.accountservice.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountMapper accountMapper;

    /**
     * Process Credit/Debit Transaction
     */
    public AccountResponse processTransaction(TransactionRequest request) {

        // Idempotency Check
        if (transactionRepository.existsByEventId(request.getEventId())) {
            throw new DuplicateTransactionException(
                    "Event already processed : " + request.getEventId());
        }

        // Auto Create Account
        Account account = accountRepository.findById(request.getAccountId())
                .orElse(Account.builder()
                        .accountId(request.getAccountId())
                        .balance(BigDecimal.ZERO)
                        .build());

        // Out-of-Order Event Check
        if (account.getLastEventTimestamp() != null
                && request.getEventTimestamp()
                        .isBefore(account.getLastEventTimestamp())) {

            throw new IllegalArgumentException(
                    "Out-of-order event received");
        }

        // Credit Transaction
        if (request.getTransactionType() == TransactionType.CREDIT) {

            account.setBalance(
                    account.getBalance().add(request.getAmount()));

        }
        // Debit Transaction
        else {

            if (account.getBalance()
                    .compareTo(request.getAmount()) < 0) {

                throw new InsufficientBalanceException(
                        "Insufficient balance");
            }

            account.setBalance(
                    account.getBalance().subtract(request.getAmount()));
        }

        account.setLastEventTimestamp(request.getEventTimestamp());

        // Save Account
        account = accountRepository.save(account);

        // Save Transaction
        Transaction transaction = accountMapper.toTransactionEntity(request);

        transaction.setTransactionId(UUID.randomUUID().toString());

        transactionRepository.save(transaction);

        // Fetch Latest 10 Transactions
        List<Transaction> transactions =
                transactionRepository
                        .findTop10ByAccountIdOrderByEventTimestampDesc(
                                account.getAccountId());

        return accountMapper.toAccountResponse(account, transactions);
    }

    /**
     * Get Account Details
     */
    public AccountResponse getAccount(String accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));

        List<Transaction> transactions =
                transactionRepository
                        .findTop10ByAccountIdOrderByEventTimestampDesc(
                                accountId);

        return accountMapper.toAccountResponse(account, transactions);
    }

    /**
     * Get Account Balance
     */
    public BalanceResponse getBalance(String accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));

        return accountMapper.toBalanceResponse(account);
    }

}