package com.gatewayservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.gatewayservice.entity.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    private String eventId;

    private String accountId;

    private EventType transactionType;

    private BigDecimal amount;

    private Instant eventTimestamp;

}