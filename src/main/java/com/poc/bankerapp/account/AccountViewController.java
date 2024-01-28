package com.poc.bankerapp.account;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poc.bankerapp.jpa.AccountRepository;
import com.poc.bankerapp.user.User;

import jakarta.validation.Valid;

@Controller
public class AccountViewController {
	
	private AccountRepository accountRepository;

	public AccountViewController(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}
	
	@RequestMapping(value = "/list-accts",method = RequestMethod.GET )
	public String viewAccounts(ModelMap model) {
		List<Account> accounts = accountRepository.findAll();
		model.addAttribute("accounts", accounts);
		return "listAllAccounts";
	}
	
	@GetMapping(value="/add-acct")
	public String addAccountPage(ModelMap model) {
		Account account = new Account(0,0,new User());
		model.put("account", account);
		return "addAccount";
	}
	
	@GetMapping(value="/update-acct")
	public String updateAccountPage(@RequestParam int id, ModelMap model) {
		Optional<Account> account = accountRepository.findById(id);
		model.put("account", account.get());
		return "updateAccount";
	}
	
	@PostMapping(value="/update-acct")
	public String updateAccount(@Valid Account account, BindingResult result ) {
		Account oldAccount = accountRepository.findById(account.getId()).get();
		oldAccount.setBalance(account.getBalance());
		if(result.hasErrors()) {
			return "updateUser";
		}
		accountRepository.save(oldAccount);
		return "redirect:list-accts";
	}
}
