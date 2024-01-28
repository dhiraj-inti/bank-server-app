package com.poc.bankerapp.user;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.bankerapp.exception.UnauthorizedRequestException;
import com.poc.bankerapp.exception.UserNotFoundException;
import com.poc.bankerapp.jpa.UserRepository;
import com.poc.bankerapp.login.jwt.JwtUtil;
import com.poc.bankerapp.response.UserResponse;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Transactional
public class UserJpaController {

	private UserRepository userRepository;
	private JwtUtil jwtUtil;

	public UserJpaController(UserRepository userRepository,JwtUtil jwtUtil) {
		super();
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}

	@GetMapping("")
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		try {
			if (user.isEmpty())
				throw new UserNotFoundException("User:" + id + " not found");

			UserResponse userResponse = new UserResponse(true, user.get(), null);
			ResponseEntity<UserResponse> responseEntity = new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);

			return responseEntity;
		} catch (Exception e) {
			UserResponse userResponse = new UserResponse(false, null, e.getMessage());
			ResponseEntity<UserResponse> responseEntity = new ResponseEntity<UserResponse>(userResponse,
					HttpStatus.NOT_FOUND);

			return responseEntity;
		}
	}
	
	@PostMapping("/getuser")
	public ResponseEntity<UserResponse> getUserByToken(@RequestHeader("token") String token) {
		
		try {
			int id = getUserId(token);
			Optional<User> user = userRepository.findById(id);
			if (user.isEmpty())
				throw new UserNotFoundException("User:" + id + " not found");
			User userObj = user.get();
			
			UserResponse userResponse = new UserResponse(true, userObj, null);
			ResponseEntity<UserResponse> responseEntity = new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);

			return responseEntity;
		} catch (Exception e) {
			UserResponse userResponse = new UserResponse(false, null, e.getMessage());
			ResponseEntity<UserResponse> responseEntity = new ResponseEntity<UserResponse>(userResponse,
					HttpStatus.NOT_FOUND);

			return responseEntity;
		}
	}

	@DeleteMapping("/{id}/delete")
	public ResponseEntity<UserResponse> deleteUserById(@PathVariable int id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isEmpty())
				throw new UserNotFoundException("User:" + id + " not found");

			User userObj = user.get();
			UserResponse userResponse = new UserResponse(true, userObj, null);
			userRepository.deleteById(id);
			
			ResponseEntity<UserResponse> responseEntity = new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);

			return responseEntity;
		} catch (Exception e) {
			UserResponse userResponse = new UserResponse(false, null, e.getMessage());
			ResponseEntity<UserResponse> responseEntity = new ResponseEntity<UserResponse>(userResponse,
					HttpStatus.NOT_FOUND);

			return responseEntity;
		}
	}

	@PostMapping("/create")
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid User user) {
		try {
			userRepository.save(user);
			UserResponse userResponse = new UserResponse(true, user, null);
			ResponseEntity<UserResponse> responseEntity = new ResponseEntity<UserResponse>(userResponse,
					HttpStatus.CREATED);

			return responseEntity;
		} catch (Exception e) {
			UserResponse userResponse = new UserResponse(false, null, e.getMessage());
			ResponseEntity<UserResponse> responseEntity = new ResponseEntity<UserResponse>(userResponse,
					HttpStatus.INTERNAL_SERVER_ERROR);

			return responseEntity;
		}
	}
	
	private int getUserId(String token) throws UnauthorizedRequestException {
		if(token.equals(""))
			throw new UnauthorizedRequestException("Unauthorized request");
		int userId = jwtUtil.getUserIdFromToken(token);
		return userId;
	}

}
