package com.poc.bankerapp.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poc.bankerapp.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByUsername(String username);
}
