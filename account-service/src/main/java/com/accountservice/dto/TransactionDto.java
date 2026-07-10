package com.accountservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.accountservice.entity.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private String transactionId;

    private String eventId;

    private TransactionType transactionType;

    private BigDecimal amount;

    private Instant eventTimestamp;

}