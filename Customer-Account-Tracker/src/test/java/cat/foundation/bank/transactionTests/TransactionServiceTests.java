package cat.foundation.bank.transactionTests;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.http.ResponseEntity;

import cat.foundation.bank.account.Account;
import cat.foundation.bank.account.AccountRepository;
import cat.foundation.bank.customer.CustomerService;
import cat.foundation.bank.transaction.Transaction;
import cat.foundation.bank.transaction.TransactionRepository;
import cat.foundation.bank.transaction.TransactionService;

@SpringBootTest(classes=cat.foundation.bank.CustomerAccountTrackerApplication.class)
@ExtendWith(MockitoExtension.class)
 class TransactionServiceTests {
	
	@Mock
    private TransactionRepository transactionRepo;
	@Mock
    private AccountRepository accountRepo;
	@Mock
	private CustomerService customerService;
	
	@InjectMocks
    private TransactionService transactionService;
	
	//dummy account
	Account accountFrom = new Account();
	Account accountTo = new Account();
	
	@Test
	void contextLoads() {
		assertThat(transactionService).isNotNull();
	}
	
	@BeforeEach
    public void setup(){
		accountFrom.setId(1L);
		accountFrom.setAccountNumber(111111111111L);
		accountFrom.setAccountType("saving"); 
		accountFrom.setBalanceAmount(1000);
		
		accountTo.setId(2L);
		accountTo.setAccountNumber(222222222222L);
		accountTo.setAccountType("saving"); 
		accountTo.setBalanceAmount(2000);
    }
	
	
	
	@Test		// Transfer Amount From One Account To Another
	void transferAmountFromToTest() {
		double amount = 100;
		Mockito.when(accountRepo.findByAccountNumber(accountFrom.getAccountNumber())).thenReturn(accountFrom);
		Mockito.when(accountRepo.findByAccountNumber(accountTo.getAccountNumber())).thenReturn(accountTo);
		
		accountFrom.setBalanceAmount(accountFrom.getBalanceAmount()-amount);
		accountFrom.setBalanceAmount(accountFrom.getBalanceAmount()+amount);
		
		Mockito.when(accountRepo.save(accountFrom)).thenReturn(accountFrom);
		Mockito.when(accountRepo.save(accountTo)).thenReturn(accountTo);
		Mockito.when(customerService.customerNameByAccNo(accountTo.getAccountNumber())).thenReturn("testName");
		
		ResponseEntity<Object> transaction = transactionService.transferAmountFromTo(amount, accountFrom.getAccountNumber(), accountTo.getAccountNumber());
		
		Assertions.assertEquals(200, transaction.getStatusCodeValue());
		
		Assertions.assertEquals("Account Balance Updated Successfully for Account Number "
		+ accountTo.getAccountNumber() + " , Current Account Balance: " + accountTo.getBalanceAmount(), transaction.getBody());
		
	}
	
	@Test	//Test to Get All Transactions
	void transactionsListTest() {
		
		List<Transaction> list = new ArrayList<>();
		list.add(new Transaction());
		
		Mockito.when(transactionRepo.findAll()).thenReturn(list);
		
		ResponseEntity<Object> transactions = transactionService.transactionList();
		
		Assertions.assertEquals(list, transactions.getBody());
		Assertions.assertEquals(200, transactions.getStatusCodeValue());
		
	}

}
