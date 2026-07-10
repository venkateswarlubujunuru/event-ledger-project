package com.gatewayservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gatewayservice.client.AccountServiceClient;
import com.gatewayservice.dto.EventRequest;
import com.gatewayservice.dto.EventResponse;
import com.gatewayservice.entity.Event;
import com.gatewayservice.entity.EventStatus;
import com.gatewayservice.exception.DuplicateEventException;
import com.gatewayservice.exception.ResourceNotFoundException;
import com.gatewayservice.mapper.EventMapper;
import com.gatewayservice.repository.EventRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final AccountServiceClient accountServiceClient;

    @CircuitBreaker(name = "accountService", fallbackMethod = "processFallback")
    @Retry(name = "accountService")
    public EventResponse createEvent(EventRequest request) {

        if (eventRepository.existsByEventId(request.getEventId())) {
            throw new DuplicateEventException(
                    "Duplicate Event : " + request.getEventId());
        }

        Event event = eventMapper.toEntity(request);

        event.setStatus(EventStatus.RECEIVED);

        event = eventRepository.save(event);

        accountServiceClient.processTransaction(request);

        event.setStatus(EventStatus.PROCESSED);

        eventRepository.save(event);

        return eventMapper.toResponse(event);
    }

    public EventResponse processFallback(
            EventRequest request,
            Exception ex) {

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found"));

        event.setStatus(EventStatus.FAILED);

        eventRepository.save(event);

        return eventMapper.toResponse(event);
    }

    public EventResponse getEventById(String eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found : " + eventId));

        return eventMapper.toResponse(event);
    }

    public List<EventResponse> getEventsByAccount(String accountId) {

        return eventRepository
                .findByAccountIdOrderByEventTimestampAsc(accountId)
                .stream()
                .map(eventMapper::toResponse)
                .toList();
    }
}