package cat.foundation.bank.transactionTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import cat.foundation.bank.account.Account;
import cat.foundation.bank.transaction.Transaction;

@SpringBootTest(classes = cat.foundation.bank.CustomerAccountTrackerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 class TransactionControllerIT {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	// dummy account
	Account accountFrom = new Account();
	Account accountTo = new Account();

	@BeforeEach
	public void setup() {
		
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
	void testTransferAmountFromTo() throws JSONException, URISyntaxException {
		
		restTemplate
		.postForEntity("http://localhost:" + port + "/account/create", accountFrom, String.class);
		restTemplate
		.postForEntity("http://localhost:" + port + "/account/create", accountTo, String.class);
		
		long amount = 100;
		
		URI uri = new URI("http://localhost:"+port+"/TransferAmount/"+amount+"/From/"
								+accountFrom.getAccountNumber()+"/To/"+accountTo.getAccountNumber());
		
		HttpEntity<Transaction> httpEntity = new HttpEntity<Transaction>(headers);
		
		ResponseEntity<Object> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, Object.class);
	
		assertEquals(404, responseEntity.getStatusCodeValue());
	}
	
	@Test	//Test to Get All Transactions
	void testAllTransactions() {
		
		ResponseEntity<Object> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/transactions", Object.class);
		assertEquals(404, responseEntity.getStatusCodeValue());
	}

}
