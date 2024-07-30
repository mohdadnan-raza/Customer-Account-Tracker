package cat.foundation.bank.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	AccountService accountService;

	// Create New Account
	@PostMapping(path="/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createAccount(@RequestBody AccountDTO accountDto) {
		Account account = accountDto.convertToEntity();
		return accountService.createAccount(account);
	}

	// List of Accounts
	@GetMapping(path="/list", produces = "application/json")
	public ResponseEntity<Object> findAllAccounts() {
		return accountService.findAllAccounts();
	}

	// Update Existing Account
	@PutMapping("/update")
	public ResponseEntity<Object> updateAccount(@RequestBody AccountDTO accountDto) {
		Account account = accountDto.convertToEntity();
		return accountService.updateAccount(account);
	}

	// Delete Account by Account_Number
	@DeleteMapping("/delete/accountNo/{accNo}")
	public ResponseEntity<Object> deleteAccount(@PathVariable long accNo) {
		return accountService.deleteAccountByAccNo(accNo);
	}

	// find Account by Account_Number
	@GetMapping("/{accountNumber}")
	public ResponseEntity<Object> findByAccountNumber(@PathVariable long accountNumber) {
		return accountService.findByAccountNumber(accountNumber);
	}

	// find Account by Account_Id
	@GetMapping("/id/{id}")
	public ResponseEntity<Account> findAccountById(@PathVariable long id) {
		return accountService.findAccountById(id);
	}
	
}
