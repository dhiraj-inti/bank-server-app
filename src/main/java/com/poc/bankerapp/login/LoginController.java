package com.poc.bankerapp.login;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.poc.bankerapp.exception.UserNotFoundException;
import com.poc.bankerapp.jpa.UserRepository;
import com.poc.bankerapp.login.jwt.JwtUtil;
import com.poc.bankerapp.request.LoginRequest;
import com.poc.bankerapp.response.LoginResponse;
import com.poc.bankerapp.user.User;

@Controller
public class LoginController {

	private UserRepository userRepository;
	
	private JwtUtil jwtUtil;

	public LoginController(UserRepository userRepository,JwtUtil jwtUtil) {
		super();
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> loginByUser(@RequestBody LoginRequest loginRequest) {
		try {
			String username = loginRequest.getUsername();
			String sw0rd = loginRequest.getPassword();
			Optional<User> user = userRepository.findByUsername(username);
			if (user.isEmpty())
				throw new UserNotFoundException("User:" + username + " not found");

			LoginResponse loginResponse;
			User userObj = user.get();
			if (sw0rd.equals(userObj.getPassword())) {
				String token = jwtUtil.generateToken(userObj.getId());
//				loginResponse = new LoginResponse(String.valueOf(userObj.getId()), true, null);
				loginResponse = new LoginResponse(token, true, null);
				ResponseEntity<LoginResponse> responseEntity = new ResponseEntity<LoginResponse>(loginResponse,
						HttpStatus.OK);
				return responseEntity;
			} else {
				loginResponse = new LoginResponse(null, false, "Bad Credentials");
				ResponseEntity<LoginResponse> responseEntity = new ResponseEntity<LoginResponse>(loginResponse,
						HttpStatus.UNAUTHORIZED);
				return responseEntity;
			}


		} catch (Exception e) {
			LoginResponse loginResponse = new LoginResponse(null, false, e.getMessage());
			ResponseEntity<LoginResponse> responseEntity = new ResponseEntity<LoginResponse>(loginResponse,
					HttpStatus.INTERNAL_SERVER_ERROR);

			return responseEntity;
		}
	}
	
}
