package com.accountservice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.accountservice.dto.AccountResponse;
import com.accountservice.dto.BalanceResponse;
import com.accountservice.dto.TransactionDto;
import com.accountservice.dto.TransactionRequest;
import com.accountservice.entity.Account;
import com.accountservice.entity.Transaction;

@Component
public class AccountMapper {

    /**
     * Convert TransactionRequest -> Transaction Entity
     */
    public Transaction toTransactionEntity(TransactionRequest request) {

        return Transaction.builder()
                .eventId(request.getEventId())
                .accountId(request.getAccountId())
                .transactionType(request.getTransactionType())
                .amount(request.getAmount())
                .eventTimestamp(request.getEventTimestamp())
                .build();
    }

    /**
     * Convert Transaction Entity -> TransactionDto
     */
    public TransactionDto toTransactionDto(Transaction transaction) {

        return TransactionDto.builder()
                .transactionId(transaction.getTransactionId())
                .eventId(transaction.getEventId())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .eventTimestamp(transaction.getEventTimestamp())
                .build();
    }

    /**
     * Convert Account + Transactions -> AccountResponse
     */
    public AccountResponse toAccountResponse(Account account,
                                             List<Transaction> transactions) {

        List<TransactionDto> transactionDtos = transactions.stream()
                .map(this::toTransactionDto)
                .collect(Collectors.toList());

        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .balance(account.getBalance())
                .recentTransactions(transactionDtos)
                .build();
    }

    /**
     * Convert Account -> BalanceResponse
     */
    public BalanceResponse toBalanceResponse(Account account) {

        return BalanceResponse.builder()
                .accountId(account.getAccountId())
                .balance(account.getBalance())
                .build();
    }

}