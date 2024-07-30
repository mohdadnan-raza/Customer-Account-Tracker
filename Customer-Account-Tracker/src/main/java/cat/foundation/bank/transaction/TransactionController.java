package cat.foundation.bank.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
	
	Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	TransactionService transactionService;
	
	//Transfer Amount
	@PutMapping("/TransferAmount/{amount}/From/{fromAccNo}/To/{toAccNo}")
	public ResponseEntity<Object> transferAmountFromTo(@PathVariable long fromAccNo, @PathVariable long toAccNo, @PathVariable double amount) {
		return transactionService.transferAmountFromTo(amount, fromAccNo, toAccNo);
	}
	
	//List of Transactions
	@GetMapping("/transactions")
	public ResponseEntity<Object> allTransactions(){
		return transactionService.transactionList();
	}

}
