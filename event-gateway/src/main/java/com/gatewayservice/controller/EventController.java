package com.gatewayservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.gatewayservice.dto.EventRequest;
import com.gatewayservice.dto.EventResponse;
import com.gatewayservice.entity.Event;
import com.gatewayservice.mapper.EventMapper;
import com.gatewayservice.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    /**
     * Create Event
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(@Valid @RequestBody EventRequest request) {

        Event event = eventMapper.toEntity(request);

        Event savedEvent = eventService.createEvent(event);

        return eventMapper.toResponse(savedEvent);
    }

    /**
     * Get Event by Id
     */
    @GetMapping("/{eventId}")
    public EventResponse getEventById(@PathVariable String eventId) {

        Event event = eventService.getEventById(eventId);

        return eventMapper.toResponse(event);
    }

    /**
     * Get Events by Account
     */
    @GetMapping
    public List<EventResponse> getEventsByAccount(
            @RequestParam("account") String accountId) {

        return eventService.getEventsByAccount(accountId)
                .stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());
    }

}