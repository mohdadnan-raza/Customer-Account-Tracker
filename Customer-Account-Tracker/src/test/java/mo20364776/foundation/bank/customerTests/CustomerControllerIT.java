package mo20364776.foundation.bank.customerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
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
import cat.foundation.bank.customer.Customer;

@SpringBootTest(classes = cat.foundation.bank.CustomerAccountTrackerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerIT {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	// dummy customer
	Customer customer = new Customer();
	// dummy account
	Account account = new Account();

	@BeforeEach
	public void setup() {
		customer.setId(1L);
		customer.setName("test");
		customer.setEmail("test@test.com");
		customer.setAadharNo(111111111111L);
		customer.setContact(9876543210L);

		account.setId(1L);
		account.setAccountNumber(111111111111L);
		account.setAccountType("saving");
		account.setBalanceAmount(1000);
	}

	@Test		//Test for Create Customer
	void testCreateCustomer() throws JSONException {

		ResponseEntity<String> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/customer/create", customer, String.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test		//Test for All Customer List
	void testAllCustomers() {

		ResponseEntity<Object> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/customer/list", Object.class);
		Assertions.assertTrue(200==responseEntity.getStatusCodeValue() || 404==responseEntity.getStatusCodeValue());
	}

	@Test		//Test for Update Customer Details
	void testUpdateCustomer() throws JSONException, URISyntaxException {

		URI uri = new URI("http://localhost:" + port + "/customer/update");
		HttpEntity<Customer> httpEntity = new HttpEntity<Customer>(customer, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, String.class);

		Assertions.assertTrue(200==responseEntity.getStatusCodeValue() || 404==responseEntity.getStatusCodeValue());
	}

	@Test		//Test for Find Customer By Customer Id
	void testFindCustomerById() throws URISyntaxException {

		URI uri = new URI("http://localhost:" + port + "/customer/id/" + customer.getId());
		HttpEntity<Customer> httpEntity = new HttpEntity<Customer>(customer, headers);

		ResponseEntity<Customer> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity,
				Customer.class);

		Assertions.assertTrue(200==responseEntity.getStatusCodeValue() || 404==responseEntity.getStatusCodeValue());
	}
	
	@Test	//Test for Add Account to Customer
	void testAddAccount() throws URISyntaxException {
		
		URI uri = new URI("http://localhost:"+port+"/customer/"+customer.getId()+"/addAccount/"+account.getId());
		HttpEntity<Customer> httpEntity = new HttpEntity<Customer>(customer, headers);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, String.class);
	
		Assertions.assertTrue(200==responseEntity.getStatusCodeValue() || 404==responseEntity.getStatusCodeValue());
	}
	
	@Test	//Test for Find Customer By Account Number
	void testFindCustomerByAccNo() throws URISyntaxException {
		
		URI uri = new URI("http://localhost:"+port+"/customer/accountNumber/"+account.getAccountNumber());
		HttpEntity<Customer> httpEntity = new HttpEntity<Customer>(customer, headers);
		
		ResponseEntity<Customer> responseEntity = 
				restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Customer.class);
	
		Assertions.assertTrue(200==responseEntity.getStatusCodeValue() || 404==responseEntity.getStatusCodeValue());
	}
	
	@Test	//Test for Remove Account from Customer
	void testRemoveAccount() throws URISyntaxException {
		
		URI uri = new URI("http://localhost:"+port+"/customer/"+customer.getId()+"/removeAccount/"+account.getId());
		HttpEntity<Customer> httpEntity = new HttpEntity<Customer>(customer, headers);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, String.class);
	
		Assertions.assertTrue(200==responseEntity.getStatusCodeValue() || 404==responseEntity.getStatusCodeValue());
	}
	
	@Test	//Test for Delete Customer By Id
	void testDeleteCustomerById() throws URISyntaxException {

		URI uri = new URI("http://localhost:" + port + "/customer/delete/id/" + customer.getId());
		HttpEntity<Customer> httpEntity = new HttpEntity<Customer>(customer, headers);

		ResponseEntity<Customer> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity,
				Customer.class);

		Assertions.assertTrue(200==responseEntity.getStatusCodeValue() || 404==responseEntity.getStatusCodeValue());
	}
}
