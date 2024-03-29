package com.poc.bankerapp.response;

public class LoginResponse {

	private String token;

	private boolean success;

	private String message;

	public LoginResponse(String token, boolean success, String message) {
		super();
		this.token = token;
		this.success = success;
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "LoginResponse [token=" + token + ", success=" + success + ", message=" + message + "]";
	}

}
