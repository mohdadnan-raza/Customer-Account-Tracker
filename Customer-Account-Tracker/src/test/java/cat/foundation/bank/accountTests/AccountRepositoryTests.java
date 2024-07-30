package cat.foundation.bank.accountTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cat.foundation.bank.account.Account;
import cat.foundation.bank.account.AccountRepository;

@SpringBootTest(classes = cat.foundation.bank.CustomerAccountTrackerApplication.class)
 class AccountRepositoryTests {

	@Autowired
	private AccountRepository accountRepo;

	@Test				//Test for SAVE
	void testSave() {
		Account account = getAccount();
		accountRepo.save(account);
		Account found = accountRepo.findById(account.getId()).get();
		assertEquals(account.getId(), found.getId());
	}
	
	@Test				//Test for FIND BY ID
	 void testFindById() {
		Account account = getAccount();
		accountRepo.save(account);
		Account result1 = accountRepo.findById(account.getId()).get();
		Account result2 = accountRepo.findAccountById(account.getId());
		assertEquals(account.getId(), result1.getId());
		assertEquals(account.getId(), result2.getId());
	}
	
	@Test				//Test for FIND BY ACCOUNT NUMBER
	 void testFindByAccountNumber() {
		Account account = getAccount();
		accountRepo.save(account);
		Account savedAccount = accountRepo.findById(account.getId()).get();
		Account result = accountRepo.findByAccountNumber(savedAccount.getAccountNumber());
		assertEquals(savedAccount, result);
	}

	@Test				//Test for FIND ALL
	void testFindAll() {
		Account account = getAccount();
		accountRepo.save(account);
		long count = accountRepo.count();
		List<Account> result = new ArrayList<>();
		accountRepo.findAll().forEach(e -> result.add(e));
		assertEquals(count, result.size());
	}
	
	@Test				//Test for EXISTS BY ACCOUNT NUMBER
	 void testExistsByAccountNumber() {
		Account account = getAccount();
		accountRepo.save(account);
		Account savedAccount = accountRepo.findById(account.getId()).get();
		
		boolean checkById = accountRepo.existsById(account.getId());
		boolean checkByAccountNumber = accountRepo.existsByAccountNumber(savedAccount.getAccountNumber());
		
		assertEquals(checkById, checkByAccountNumber);
	}
	
	@Test				//Test for DELETE BY ID
	void testDeleteById() {
		Account account = getAccount();
		accountRepo.save(account);
		accountRepo.findAll().forEach(a->accountRepo.deleteById(a.getId()));
		List<Account> result = new ArrayList<>();
		accountRepo.findAll().forEach(e -> result.add(e));
		assertEquals(0, result.size());
	}

	
	//Dummy Account
	private Account getAccount() {
		Account account = new Account();
		account.setId(1L);
		account.setAccountType("saving");
		account.setBalanceAmount(1000);
		return account;
	}
}
