package cat.foundation.bank.transactionTests;

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
import cat.foundation.bank.transaction.TransactionController;
import cat.foundation.bank.transaction.TransactionService;

@SpringBootTest(classes = cat.foundation.bank.CustomerAccountTrackerApplication.class)
@ExtendWith(MockitoExtension.class)
 class TransactionControllerTests {
	
	@Mock
    private TransactionService transactionService;
	
	@InjectMocks
    private TransactionController transactionController;
	
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
		transactionController.transferAmountFromTo(accountFrom.getAccountNumber(), accountTo.getAccountNumber(), 100);
		verify(transactionService, times(1)).transferAmountFromTo(100, accountFrom.getAccountNumber(), accountTo.getAccountNumber());
	}
	

	@Test	//Test to Get All Transactions
	void transactionListTest() {
		transactionController.allTransactions();
		verify(transactionService, times(1)).transactionList();
	}
}
