package com.gatewayservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.gatewayservice.entity.EventType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    @NotBlank(message = "Event Id is required")
    private String eventId;

    @NotBlank(message = "Account Id is required")
    private String accountId;

    @NotNull(message = "Event type is required")
    private EventType type;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotNull(message = "Event Timestamp is required")
    private Instant eventTimestamp;

    @Valid
    private MetadataDto metadata;

}