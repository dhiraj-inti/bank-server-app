package com.poc.bankerapp.response;

import com.poc.bankerapp.user.User;

public class UserResponse {

	private boolean success;
	private User user;
	private String message;

	public UserResponse(boolean success, User user, String message) {
		super();
		this.success = success;
		this.user = user;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "UserResponse [success=" + success + ", user=" + user + ", message=" + message + "]";
	}

}
