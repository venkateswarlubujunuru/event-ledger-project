package com.gatewayservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gatewayservice.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    /**
     * Check whether an event already exists.
     * Used for Idempotency.
     */
    boolean existsByEventId(String eventId);

    /**
     * Fetch all events of an account ordered by event timestamp.
     * Used for out-of-order event processing.
     */
    List<Event> findByAccountIdOrderByEventTimestampAsc(String accountId);

}