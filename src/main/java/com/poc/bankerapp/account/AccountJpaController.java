package com.poc.bankerapp.account;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.bankerapp.exception.AccountNotFoundException;
import com.poc.bankerapp.exception.InsufficientBalanceException;
import com.poc.bankerapp.exception.UnauthorizedRequestException;
import com.poc.bankerapp.exception.UserNotFoundException;
import com.poc.bankerapp.jpa.AccountRepository;
import com.poc.bankerapp.jpa.UserRepository;
import com.poc.bankerapp.login.jwt.JwtUtil;
import com.poc.bankerapp.response.AccountResponse;
import com.poc.bankerapp.user.User;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
@Transactional
public class AccountJpaController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	/*
	 * URL: "/api/accounts"
	 * The method handles the request hitting this API url
	 * @return accounts List of all the accounts in the repository
	 */
	@GetMapping("")
	public List<Account> getAccounts(){
		List<Account> accounts = accountRepository.findAll();
		return accounts;
	}
	
	/*
	 * URL: "/api/accounts/{id}
	 * @param @PathVariable accountId id of the account
	 * @return account Account retrieved from the repository
	 */
	@GetMapping("/{id}")
	public ResponseEntity<AccountResponse> getAccount(@PathVariable("id") int accountId) {
		
		try {
			Optional<Account> account = accountRepository.findById(accountId);
			if(account.isEmpty())
				throw new AccountNotFoundException("Account Number:"+ accountId + " not found");
			
			AccountResponse accountResponse = new AccountResponse(true, account.get(),null);
			ResponseEntity<AccountResponse> responseEntity = 
					new ResponseEntity<AccountResponse>(accountResponse,HttpStatus.OK);
	
			return responseEntity;
		}
		catch(Exception e) {
			AccountResponse accountResponse = new AccountResponse(false, null, e.getMessage());
			ResponseEntity<AccountResponse> responseEntity = 
					new ResponseEntity<AccountResponse>(accountResponse,HttpStatus.NOT_FOUND);
	
			return responseEntity;
		}
	}
	
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<AccountResponse> deleteAccountById(@PathVariable int id){
		try {
			Optional<Account> account = accountRepository.findById(id);
			if(account.isEmpty())
				throw new AccountNotFoundException("Account Number:"+ id + " not found");
			Account accountObj = account.get();
			int userId = accountObj.getUser().getId();
			// Retrieve the user entity from the database
	        Optional<User> userOptional = userRepository.findById(userId);
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            // Find the account to remove from the user's list of accounts
	            List<Account> accounts = user.getAccounts();
	            Iterator<Account> iterator = accounts.iterator();
	            while (iterator.hasNext()) {
	                Account accountItr = iterator.next();
	                if (accountItr.getId() == id) {
	                    // Remove the account from the list of accounts associated with the user
	                    iterator.remove();
	                    break; // Exit the loop once the account is found and removed
	                }
	            }
	            // Save the user entity back to the database
	            userRepository.save(user);
	        } else {
	            throw new UserNotFoundException("User not found with ID: " + userId);
	        }
		
			AccountResponse accountResponse = new AccountResponse(true, accountObj,null);
			accountRepository.deleteById(id);
			ResponseEntity<AccountResponse> responseEntity = 
					new ResponseEntity<AccountResponse>(accountResponse,HttpStatus.OK);
			
			return responseEntity;
		}
		catch(Exception e) {
			AccountResponse accountResponse = new AccountResponse(false, null, e.getMessage());
			ResponseEntity<AccountResponse> responseEntity = 
					new ResponseEntity<AccountResponse>(accountResponse,HttpStatus.INTERNAL_SERVER_ERROR);
			
			return responseEntity;
		}
	}
	
	@PatchMapping("/{id}/withdraw/{amount}")
	public ResponseEntity<AccountResponse> withdrawAmount(@RequestHeader("token") String token,@PathVariable int id, @PathVariable int amount){
		try {
			int userId = getUserId(token);
			Optional<Account> account = accountRepository.findById(id);
			if(account.get().getUser().getId()!=userId)
				throw new UnauthorizedRequestException("Unauthorized request");
			if(account.isEmpty())
				throw new AccountNotFoundException("Account Number:"+ id + " not found");
			Account accountObject = account.get();
			if(amount<=accountObject.getBalance())
				accountObject.setBalance(accountObject.getBalance() - amount);
			else
				throw new InsufficientBalanceException("Insufficient balance");
			Account savedAccount = accountRepository.save(accountObject);
			AccountResponse accountResponse = new AccountResponse(true, savedAccount,null);
			ResponseEntity<AccountResponse> responseEntity = 
					new ResponseEntity<AccountResponse>(accountResponse,HttpStatus.OK);
			
			return responseEntity;
		}
		catch(Exception e) {
			AccountResponse accountResponse = new AccountResponse(false, null, e.getMessage());
			ResponseEntity<AccountResponse> responseEntity = 
					new ResponseEntity<AccountResponse>(accountResponse,HttpStatus.INTERNAL_SERVER_ERROR);
			
			return responseEntity;
		}
	}
	
	@PatchMapping("/{id}/deposit/{amount}")
	public ResponseEntity<AccountResponse> depositAmount(@RequestHeader("token") String token,@PathVariable int id, @PathVariable int amount){
		try {
			int userId = getUserId(token);
			Optional<Account> account = accountRepository.findById(id);
			if(account.get().getUser().getId()!=userId)
				throw new UnauthorizedRequestException("Unauthorized request");
			if(account.isEmpty())
				throw new AccountNotFoundException("Account Number:"+ id + " not found");
			Account accountObject = account.get();
			if(amount>0)
				accountObject.setBalance(accountObject.getBalance() + amount);
			else
				throw new Exception("Invalid deposit amount,it must be greater than 0");
			Account savedAccount = accountRepository.save(accountObject);
			AccountResponse accountResponse = new AccountResponse(true, savedAccount,null);
			ResponseEntity<AccountResponse> responseEntity = 
					new ResponseEntity<AccountResponse>(accountResponse,HttpStatus.OK);
			
			return responseEntity;
		}
		catch(Exception e) {
			AccountResponse accountResponse = new AccountResponse(false, null, e.getMessage());
			ResponseEntity<AccountResponse> responseEntity = 
					new ResponseEntity<AccountResponse>(accountResponse,HttpStatus.INTERNAL_SERVER_ERROR);
			
			return responseEntity;
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<AccountResponse> createAccount(@RequestBody @Valid Account account){
		try {
			if(!userRepository.existsById(account.getUser().getId()))
				throw new UserNotFoundException("User does not exist");
			accountRepository.save(account);
			AccountResponse accountResponse = new AccountResponse(true, account,null);
			ResponseEntity<AccountResponse> responseEntity = 
					new ResponseEntity<AccountResponse>(accountResponse,HttpStatus.CREATED);
			
			return responseEntity;
		}
		catch(Exception e) {
			AccountResponse accountResponse = new AccountResponse(false, null, e.getMessage());
			ResponseEntity<AccountResponse> responseEntity = 
					new ResponseEntity<AccountResponse>(accountResponse,HttpStatus.INTERNAL_SERVER_ERROR);
			
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
