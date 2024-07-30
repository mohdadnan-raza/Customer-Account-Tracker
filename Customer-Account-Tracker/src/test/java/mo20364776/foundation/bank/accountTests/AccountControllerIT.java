package mo20364776.foundation.bank.accountTests;

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


@SpringBootTest(classes = cat.foundation.bank.CustomerAccountTrackerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerIT {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	// dummy account
	Account account = new Account();

	@BeforeEach   
	public void setup() {
		account.setId(1L);
		account.setAccountNumber(111111111111L);
		account.setAccountType("saving");
		account.setBalanceAmount(1000);
	}

	@Test		//Test for Create Account
	void testCreateAccount() throws JSONException {

		ResponseEntity<String> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/account/create", account, String.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test		//Test for All Account List
	void testAllAccounts() {
		
		ResponseEntity<Object> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/account/list", Object.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test		//Test for Find Account By Id
	void testFindAccountById() throws URISyntaxException {
		
		URI uri = new URI("http://localhost:"+port+"/account/id/"+account.getId());
		HttpEntity<Account> httpEntity = new HttpEntity<Account>(account, headers);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
	
		assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test		//Test for Update Account Details
	void testUpdateAccount() throws JSONException, URISyntaxException {

		URI uri = new URI("http://localhost:"+port+"/account/update");
		HttpEntity<Account> httpEntity = new HttpEntity<Account>(account, headers);
		
		ResponseEntity<Object> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, Object.class);
	
		assertEquals(204, responseEntity.getStatusCodeValue());
	}
	
	@Test		//Test for Delete Account By Account Number
	void testDeleteAccount() throws URISyntaxException {
		
		URI uri = new URI("http://localhost:"+port+"/account/delete/accountNo/"+account.getAccountNumber());
		HttpEntity<Account> httpEntity = new HttpEntity<Account>(account, headers);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity, String.class);
	
		assertEquals(404, responseEntity.getStatusCodeValue());
	}
	
	@Test		//Test for Find Account By Account Number
	void testFindByAccountNumber() throws URISyntaxException {
		
		URI uri = new URI("http://localhost:"+port+"/account/"+account.getAccountNumber());
		HttpEntity<Account> httpEntity = new HttpEntity<Account>(account, headers);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
	
		assertEquals(404, responseEntity.getStatusCodeValue());
	}
	
	

}
