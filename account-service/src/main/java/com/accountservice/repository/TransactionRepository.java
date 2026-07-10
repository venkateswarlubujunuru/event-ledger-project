package com.accountservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accountservice.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    /**
     * Check duplicate event processing.
     */
    boolean existsByEventId(String eventId);

    /**
     * Fetch all transactions of an account.
     */
    List<Transaction> findByAccountIdOrderByEventTimestampAsc(String accountId);

    List<Transaction> findTop10ByAccountIdOrderByEventTimestampDesc(String accountId);
}