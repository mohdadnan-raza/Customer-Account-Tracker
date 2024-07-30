package mo20364776.foundation.bank.transactionTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cat.foundation.bank.transaction.Transaction;
import cat.foundation.bank.transaction.TransactionRepository;

@SpringBootTest(classes = cat.foundation.bank.CustomerAccountTrackerApplication.class)
 class TransactionRepositoryTests {
	
	@Autowired
	private TransactionRepository transactionRepo;

	@Test				//FIND BY ID
	 void testFindById() {
		Transaction transaction = getTransaction();
		transactionRepo.save(transaction);
		Transaction result = transactionRepo.findById(transaction.getId()).get();
		assertEquals(transaction.getId(), result.getId());
	}

	@Test				//FIND ALL
	void testFindAll() {
		Transaction transaction = getTransaction();
		transactionRepo.save(transaction);
		List<Transaction> result = new ArrayList<>();
		transactionRepo.findAll().forEach(e -> result.add(e));
		assertEquals(1, result.size());
	}

	@Test				//SAVE
	void testSave() {
		Transaction transaction = getTransaction();
		transactionRepo.save(transaction);
		Transaction found = transactionRepo.findById(transaction.getId()).get();
		assertEquals(transaction.getId(), found.getId());
	}

	@Test				//DELETE BY ID
	void testDeleteById() {
		Transaction transaction = getTransaction();
		transactionRepo.save(transaction);
		transactionRepo.deleteById(transaction.getId());
		List<Transaction> result = new ArrayList<>();
		transactionRepo.findAll().forEach(e -> result.add(e));
		assertEquals(0, result.size());
	}

	
	//Dummy Transaction
	private Transaction getTransaction() {
		Transaction transaction = new Transaction();
		transaction.setId(1L);
		transaction.setAmount(1000);
		transaction.setBeneficiaryAccNumber(111111111111L);
		transaction.setSendersAccNumber(222222222222L);
		transaction.setBeneficiaryName("test");
		return transaction;
	}

}
