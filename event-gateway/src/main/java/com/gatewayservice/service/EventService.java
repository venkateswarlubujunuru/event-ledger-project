package com.gatewayservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gatewayservice.dto.EventRequest;
import com.gatewayservice.entity.Event;
import com.gatewayservice.entity.EventStatus;
import com.gatewayservice.exception.DuplicateEventException;
import com.gatewayservice.exception.ResourceNotFoundException;
import com.gatewayservice.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    /**
     * Create a new Event
     */
    public Event createEvent(Event event) {

        if (eventRepository.existsByEventId(event.getEventId())) {
            throw new DuplicateEventException(
                    "Event already exists with id : " + event.getEventId());
        }

        event.setStatus(EventStatus.RECEIVED);

        return eventRepository.save(event);
    }

    /**
     * Get Event by Id
     */
    public Event getEventById(String eventId) {

        return eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found : " + eventId));
    }

    /**
     * Get Events of Account
     */
    public List<Event> getEventsByAccount(String accountId) {

        return eventRepository
                .findByAccountIdOrderByEventTimestampAsc(accountId);
    }

}