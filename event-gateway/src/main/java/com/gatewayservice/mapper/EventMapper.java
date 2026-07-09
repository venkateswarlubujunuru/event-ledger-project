package com.gatewayservice.mapper;

import org.springframework.stereotype.Component;

import com.gatewayservice.dto.EventRequest;
import com.gatewayservice.dto.EventResponse;
import com.gatewayservice.dto.MetadataDto;
import com.gatewayservice.entity.Event;
import com.gatewayservice.entity.Metadata;

@Component
public class EventMapper {

    public Event toEntity(EventRequest request) {

        Metadata metadata = null;

        if (request.getMetadata() != null) {
            MetadataDto dto = request.getMetadata();

            metadata = new Metadata(
                    dto.getSource(),
                    dto.getBatchId());
        }

        return Event.builder()
                .eventId(request.getEventId())
                .accountId(request.getAccountId())
                .type(request.getType())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .eventTimestamp(request.getEventTimestamp())
                .metadata(metadata)
                .build();
    }

    public EventResponse toResponse(Event event) {

        return EventResponse.builder()
                .eventId(event.getEventId())
                .accountId(event.getAccountId())
                .type(event.getType())
                .currency(event.getCurrency())
                .status(event.getStatus())
                .eventTimestamp(event.getEventTimestamp())
                .createdAt(event.getCreatedAt())
                .build();
    }
}