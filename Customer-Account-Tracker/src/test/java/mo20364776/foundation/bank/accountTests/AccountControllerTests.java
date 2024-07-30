package mo20364776.foundation.bank.accountTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import cat.foundation.bank.account.Account;
import cat.foundation.bank.account.AccountController;
import cat.foundation.bank.account.AccountDTO;
import cat.foundation.bank.account.AccountService;

@SpringBootTest(classes = cat.foundation.bank.CustomerAccountTrackerApplication.class)
@ExtendWith(MockitoExtension.class)
 class AccountControllerTests {

	@Mock
	private AccountService accountService;

	@InjectMocks
	private AccountController accountController;

	// Dummy AccountDTO
	AccountDTO accountDto = new AccountDTO();
	Account account = new Account();

	@BeforeEach
	public void setup() {
		accountDto.setId(1L);
		accountDto.setAccountNumber(111111111111L);
		accountDto.setAccountType("saving");
		accountDto.setBalanceAmount(1000);
		
		account.setId(1L);
		account.setAccountNumber(111111111111L);
		account.setAccountType("saving");
		account.setBalanceAmount(1000);
	}

	@Test
	void contextLoads() {
		assertThat(accountController).isNotNull();
	}

	@Test		//Test for Create Account
	void createAccountTest() {
		accountController.createAccount(accountDto);
		Account account = accountDto.convertToEntity();
		verify(accountService, times(1)).createAccount(account);
	}
	
	@Test		//Test for All Account List
	void findAllAccountsTest() {
		accountController.findAllAccounts();
		verify(accountService, times(1)).findAllAccounts();
	}
	
	@Test		//Test for Update Account Details
	void updateAccountTest() {
		accountController.updateAccount(accountDto);
		Account account = accountDto.convertToEntity();
		verify(accountService, times(1)).updateAccount(account);
	}
	
	@Test		//Test for Delete Account By Account Number
	void deleteAccountTest() {
		accountController.deleteAccount(account.getAccountNumber());
		verify(accountService, times(1)).deleteAccountByAccNo(account.getAccountNumber());
	}
	
	@Test		//Test for Find Account By Account Number
	void findByAccountNumberTest() {
		accountController.findByAccountNumber(account.getAccountNumber());
		verify(accountService, times(1)).findByAccountNumber(account.getAccountNumber());
	}
	
	@Test		//Test for Find Account By Account Id
	void findAccountByIdTest() {
		accountController.findAccountById(account.getId());
		verify(accountService, times(1)).findAccountById(account.getId());
	}

}
