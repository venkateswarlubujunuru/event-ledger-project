package com.accountservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.accountservice.entity.TransactionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    @NotBlank(message = "Event Id is required")
    private String eventId;

    @NotBlank(message = "Account Id is required")
    private String accountId;

    @NotNull(message = "Transaction Type is required")
    private TransactionType transactionType;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Event Timestamp is required")
    private Instant eventTimestamp;

}