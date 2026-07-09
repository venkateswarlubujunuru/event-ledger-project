package com.gatewayservice.dto;

import java.time.Instant;
import java.time.LocalDateTime;

import com.gatewayservice.entity.EventStatus;
import com.gatewayservice.entity.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {

    private String eventId;

    private String accountId;

    private EventType type;

    private String currency;

    private EventStatus status;

    private Instant eventTimestamp;

    private LocalDateTime createdAt;

}