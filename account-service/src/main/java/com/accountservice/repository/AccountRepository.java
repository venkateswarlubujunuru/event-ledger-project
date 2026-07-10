package com.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accountservice.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}