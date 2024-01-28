package com.poc.bankerapp.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poc.bankerapp.account.Account;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

@Entity(name = "user_details")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "user_id",updatable = false)
	private int id;

	@Size(max = 240, min = 3, message = "Enter a valid username")
	@Column(unique = true)
	private String username;

	@Size(max = 240, min = 3, message = "Enter a valid password")
	private String password;

	@Size(max = 240, min = 3, message = "Enter a valid full name")
	private String fullName;

	@Size(max = 10, min = 10, message = "Enter 10 digits")
	private String contact;

	@JsonIgnoreProperties("user")
//	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Account> accounts;

	public User() {

	}

	public User(int id, @Size(max = 240, min = 3, message = "Enter a valid username") String username,
			@Size(max = 240, min = 3, message = "Enter a valid password") String password,
			@Size(max = 240, min = 3, message = "Enter a valid full name") String fullName,
			@Size(max = 10, min = 10, message = "Enter 10 digits") String contact, List<Account> accounts) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.contact = contact;
		this.accounts = accounts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", fullName=" + fullName
				+ ", contact=" + contact + ", account=" + accounts + "]";
	}

}
