package com.poc.bankerapp.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.bankerapp.account.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
