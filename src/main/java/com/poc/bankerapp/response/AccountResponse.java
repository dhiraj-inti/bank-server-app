package com.poc.bankerapp.response;

import com.poc.bankerapp.account.Account;

public class AccountResponse {
	private boolean success;
	private Account account;
	private String message;

	public AccountResponse(boolean success, Account account, String message) {
		super();
		this.success = success;
		this.account = account;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "AccountResponse [success=" + success + ", account=" + account + ", message=" + message + "]";
	}

}
