package cat.foundation.bank.account;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cat.foundation.bank.exception.InvalidDataException;
import cat.foundation.bank.exception.NotExists;
import cat.foundation.bank.exception.ResourceNotFoundException;
import cat.foundation.bank.exception.SomethingWentWrongException;

/**
 * Account Service
 * 
 * @author MO20364776
 *
 */
@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepo;
	
	@Value("${ValidAccountTypes}")
	public String[] validAccountTypes;

	
	/**
	 * Create New Account
	 * @param account
	 * @return
	 */
	public ResponseEntity<Object> createAccount(Account account) {
		
		//check if account type is valid
		if(Arrays.toString(validAccountTypes).toUpperCase().contains(account.getAccountType().toUpperCase())) {
				
			account.setAccountNumber(generateAccountNumber());
			accountRepo.save(account);

			// checks if account is added and return confirmation
			if (Boolean.TRUE.equals(accountRepo.existsById(account.getId())))
				return new ResponseEntity<>(account + " \nis Added", HttpStatus.OK);
			else
				throw new SomethingWentWrongException("Something Went Wrong: Account Is Not Added");
		}else
			throw new InvalidDataException("Invalid Account Type! Select From: "+Arrays.toString(validAccountTypes));
	}
	

	/**
	 * Update Account
	 * @param account
	 * @return
	 */
	public ResponseEntity<Object> updateAccount(Account account) {
		// if account exists then update the account and confirm
		if (Boolean.TRUE.equals(accountRepo.existsByAccountNumber(account.getAccountNumber()))) {
			account.setAccountNumber(accountRepo.findAccountById(account.getId()).getAccountNumber());
			accountRepo.save(account);
			return new ResponseEntity<>(account + "\nis Updated", HttpStatus.OK);
		}
		// if account Not exists then throw exception
		else
			throw new NotExists("Account Not Exists For Account Number: " + account.getAccountNumber());
	}
	

	/**
	 * Find Account By Id
	 * @param accountId
	 * @return
	 */
	public ResponseEntity<Account> findAccountById(long accountId) {
		// if account is present for given id then return the account and confirm
		if (accountRepo.existsById(accountId))
			return new ResponseEntity<>(accountRepo.findAccountById(accountId), HttpStatus.OK);
		// if account Not exists then throw exception
		else
			throw new ResourceNotFoundException("Account Not Found for Account Id: " + accountId);
	}
	

	/**
	 * Delete account by Account Number
	 * @param accountNo
	 * @return
	 */
	public ResponseEntity<Object> deleteAccountByAccNo(long accountNo) {
		// if account exists for given account number
		// then delete account and confirm
		if (Boolean.TRUE.equals(accountRepo.existsByAccountNumber(accountNo))) {
			accountRepo.deleteById(accountRepo.findByAccountNumber(accountNo).getId());
			return new ResponseEntity<>("Account Deleted", HttpStatus.OK);
		}
		// if account Not exists then throw exception
		else
			throw new ResourceNotFoundException("Account Not Found for Account Number: " + accountNo);
	}

	
	/**
	 * List of all accounts
	 * @return List of Accounts
	 */
	public ResponseEntity<Object> findAllAccounts() {
		// finding all accounts
		List<Account> accounts = accountRepo.findAll();
		// check if No Account is present then throw exception
		if (accounts.isEmpty())
			throw new ResourceNotFoundException("Account Not Found");
		// if accounts are present then return account list and confirm
		else if (!accounts.isEmpty())
			return new ResponseEntity<>(accounts, HttpStatus.OK);
		else
			throw new SomethingWentWrongException("Something Went Wrong");
	}
	

	/**
	 * Find Account by Account Number
	 * 
	 * @param accountNumber
	 * @return Account
	 */
	public ResponseEntity<Object> findByAccountNumber(long accountNumber) {
		// find account by account number
		if (Boolean.TRUE.equals(accountRepo.existsByAccountNumber(accountNumber))) {
			return new ResponseEntity<>(accountRepo.findByAccountNumber(accountNumber), HttpStatus.OK);
		} else
			throw new ResourceNotFoundException("Account Not Found for Account Number: " + accountNumber);
	}
	

	/**
	 * Method for generating random 12 digit Number
	 * 
	 * @return 12 digit Number
	 */
	private long generateNumber() {
		Random random = new SecureRandom();
		char[] digits = new char[12];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < 12; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return Math.abs(Long.parseLong(new String(digits)));
	}

	/**
	 * Method for generating Account Number
	 * 
	 * @return Unique Account Number
	 */
	private long generateAccountNumber() {
		String uniqueAccountNumber = "finding";
		long accountNumber = 0;

		// setting a unique account number
		while ("finding".equals(uniqueAccountNumber)) {
			accountNumber = generateNumber();
			// checking if the account number is Not already used
			if (!Boolean.TRUE.equals(accountRepo.existsByAccountNumber(accountNumber))) {
				uniqueAccountNumber = "found";
			}
		}
		return accountNumber;
	}

}
