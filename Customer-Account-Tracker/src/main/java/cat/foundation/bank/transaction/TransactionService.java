package cat.foundation.bank.transaction;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import cat.foundation.bank.account.Account;
import cat.foundation.bank.account.AccountRepository;
import cat.foundation.bank.customer.CustomerService;
import cat.foundation.bank.exception.ResourceNotFoundException;
import cat.foundation.bank.exception.SomethingWentWrongException;

/**
 * Transaction Service
 * 
 * @author MO20364776
 *
 */

@Service
public class TransactionService {

	@Autowired
	TransactionRepository transactionRepo;

	@Autowired
	AccountRepository accountRepo;

	@Autowired
	CustomerService customerService;

	
	
	/**
	 * Method To Transfer Amount From One Account To Another Account
	 * @param amount
	 * @param fromAccNo
	 * @param toAccNo
	 * @return message and 200 HttpStatus
	 */
	public ResponseEntity<Object> transferAmountFromTo(double amount, long fromAccNo, long toAccNo) {
		
		// find senders account
		Account accountFrom = Optional.ofNullable(accountRepo.findByAccountNumber(fromAccNo)).orElseThrow(
				()->new ResourceNotFoundException("account with account number " + fromAccNo + " is not present"));
		
		// find receivers account
		Account accountTo = Optional.ofNullable(accountRepo.findByAccountNumber(toAccNo)).orElseThrow(
				()->new ResourceNotFoundException("account with account number " + toAccNo + " is not present"));
		
		if (fromAccNo == toAccNo)
			throw new CannotCreateTransactionException("Invalid transaction attempt");
		
		if (amount <= 0)
			throw new CannotCreateTransactionException("Oops...Invalid amount you entered ");

		//if sufficient balance available then Make the Transaction and confirm
		if (accountFrom.getBalanceAmount() > amount) {
			//In senders account
			accountFrom.setBalanceAmount(accountFrom.getBalanceAmount() - amount);
			//In receivers account
			accountTo.setBalanceAmount(accountTo.getBalanceAmount() + amount);
			
			//save update accounts of sender and receiver
			accountRepo.save(accountFrom);
			accountRepo.save(accountTo);

			//Update Transaction details in transaction table 
			Transaction t = new Transaction();
			t.setBeneficiaryName(customerService.customerNameByAccNo(toAccNo));
			t.setBeneficiaryAccNumber(toAccNo);
			t.setAmount(amount);
			t.setSendersAccNumber(fromAccNo);
			transactionRepo.save(t);
			
			//return the confirmation message
			return new ResponseEntity<>("Account Balance Updated Successfully for Account Number "
					+ accountTo.getAccountNumber() + " , Current Account Balance: " + accountTo.getBalanceAmount(),
					HttpStatus.OK);
		}
		//if required balance is Not available then return message
		if (accountFrom.getBalanceAmount() < amount && amount > 0)
			throw new CannotCreateTransactionException("insufficient Balance");
		
		else throw new SomethingWentWrongException("Something Went Wrong");
	}

	
	
	/**
	 * List of Transactions
	 * 
	 * @return List of Transactions and 200 HttpStatus
	 */
	public ResponseEntity<Object> transactionList() {
		//find all transactions
		List<Transaction> transactions = transactionRepo.findAll();
		
		//if No transaction found then return message
		if (transactions.isEmpty())
			throw new ResourceNotFoundException("No Transaction Found");
		
		//if transactions found then return list of transactions and confirm
		else return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

}
