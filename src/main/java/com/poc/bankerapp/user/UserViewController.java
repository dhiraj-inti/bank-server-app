package com.poc.bankerapp.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poc.bankerapp.jpa.UserRepository;

import jakarta.validation.Valid;

@Controller
public class UserViewController {

	private UserRepository userRepository;

	public UserViewController(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@RequestMapping("/list-users")
	public String listUsers(ModelMap model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "listAllUsers";
	}
	
	@GetMapping("/add-user")
	public String addUser() {
		return "addUser";
	}
	
	@GetMapping(value="update-user")
	public String updateUserPage(@RequestParam int id, ModelMap model) {
		Optional<User> user = userRepository.findById(id);
		model.put("user", user.get());
		return "updateUser";
	}
	
	
	@PostMapping(value="update-user")
	public String updateUser(@Valid User user, BindingResult result ) {
		User oldUser = userRepository.findById(user.getId()).get();
		oldUser.setContact(user.getContact());
		oldUser.setFullName(user.getFullName());
		if(result.hasErrors()) {
			return "updateUser";
		}
		userRepository.save(oldUser);
		return "redirect:list-users";
	}
}
