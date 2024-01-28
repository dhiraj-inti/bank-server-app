package com.poc.bankerapp.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poc.bankerapp.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;

@Entity(name = "account_details")
public class Account {

	@Id
	@GeneratedValue
	@Column(name = "account_id")
	private int id;

	@Min(0)
	private long balance;

//	@OneToOne(cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("accounts")
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Defines the foreign key column in the Account table
    private User user;

	public Account() {

	}

	public Account(int id, long balance, User user) {
		super();
		this.id = id;
		this.balance = balance;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", user=" + user + "]";
	}

}
