package mo20364776.foundation.bank.accountTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import cat.foundation.bank.account.Account;
import cat.foundation.bank.account.AccountRepository;
import cat.foundation.bank.account.AccountService;

@SpringBootTest(classes=cat.foundation.bank.CustomerAccountTrackerApplication.class)
@ExtendWith(MockitoExtension.class)
 class AccountServiceTests {
	
	@Mock
    private AccountRepository accountRepo;
	
	@InjectMocks
    private AccountService accountService;
	
	//dummy account
	Account account = new Account();
	
	@BeforeEach
    public void setup(){
		account.setId(1L);
		account.setAccountNumber(111111111111L);
		account.setAccountType("saving"); 
		account.setBalanceAmount(1000);
    }
	
	@Test
	void contextLoads() {
		assertThat(accountService).isNotNull();
	}
	
	
    @Test		//Test for Create Account
     void createAccountTest() {
    	String[] accountTypes = {"saving", "current", "salary"};
    	accountService.validAccountTypes= accountTypes;

		Mockito.when(accountRepo.save(account)).thenReturn(account);
		Mockito.when(accountRepo.existsById(account.getId())).thenReturn(true);
		accountService.createAccount(account);
		verify(accountRepo, times(1)).save(account);
	}
	
    @Test		//Test for Update Account Details
     void updateAccountTest() {
    	Mockito.when(accountRepo.existsByAccountNumber(account.getAccountNumber())).thenReturn(true);
    	Mockito.when(accountRepo.findAccountById(account.getId())).thenReturn(account);
    	Mockito.when(accountRepo.save(any(Account.class))).thenReturn(account);
    	accountService.updateAccount(account);
    	verify(accountRepo, times(1)).save(account);
    }
    
    @Test		//Test for Find Account By Account Id
     void findAccountByIdTest() {
    	Mockito.when(accountRepo.existsById(account.getId())).thenReturn(true);
    	Mockito.when(accountRepo.findAccountById(account.getId())).thenReturn(account);
    	accountService.findAccountById(account.getId());
    	verify(accountRepo, times(1)).findAccountById(account.getId());
    }
    
    @Test		//Test for Delete Account By Account Number
     void deleteAccountByAccNoTest() {
    	Mockito.when(accountRepo.existsByAccountNumber(account.getAccountNumber())).thenReturn(true);
    	Mockito.when(accountRepo.findByAccountNumber(account.getAccountNumber())).thenReturn(account);
    	accountService.deleteAccountByAccNo(account.getAccountNumber());
    	verify(accountRepo, times(1)).deleteById(account.getId());
    }
    
    @Test		//Test for Find All Account List
    void findAllAccountsTest() {
    	List<Account> accounts = new ArrayList<>();
    	accounts.add(account);
    	Mockito.when(accountRepo.findAll()).thenReturn(accounts);
    	Assertions.assertEquals(accountService.findAllAccounts().getBody(), accounts);
    }
    
    @Test		//Test for Find Account By Account Number
    void findByAccountNumberTest() {
    	Mockito.when(accountRepo.existsByAccountNumber(account.getAccountNumber())).thenReturn(true);
    	Mockito.when(accountRepo.findByAccountNumber(account.getAccountNumber())).thenReturn(account);
    	Assertions.assertEquals(accountService.findByAccountNumber(111111111111L).getBody(), account);
    }
    

}
