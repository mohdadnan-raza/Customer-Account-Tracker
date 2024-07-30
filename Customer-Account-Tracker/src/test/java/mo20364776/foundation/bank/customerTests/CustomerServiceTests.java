package mo20364776.foundation.bank.customerTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import cat.foundation.bank.customer.Customer;
import cat.foundation.bank.customer.CustomerRepository;
import cat.foundation.bank.customer.CustomerService;

@SpringBootTest(classes = cat.foundation.bank.CustomerAccountTrackerApplication.class)
@ExtendWith(MockitoExtension.class)
 class CustomerServiceTests {
	
	@Mock
    private CustomerRepository customerRepo;
	
	@Mock
    private AccountRepository accountRepo;
	
	@InjectMocks
    private CustomerService customerService;
	
	//dummy customer
	Customer customer = new Customer();
	//dummy account
	Account account = new Account();
	
	@BeforeEach
    public void setup(){
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
	
	
	@Test
	void contextLoads() {
		assertThat(customerService).isNotNull();
	}
	
	@Test		//Test for Create Customer
	void createCustomerTest() {
		
		Mockito.when(customerRepo.existsByAadharNo(customer.getAadharNo())).thenReturn(false);
		Mockito.when(customerRepo.existsById(customer.getId())).thenReturn(true);
		
		customerService.createCustomer(customer);
		verify(customerRepo, times(1)).save(customer);
	}
	
	@Test		//Test for Update Customer Details
	void updateCustomerTest() {
		
		Mockito.when(customerRepo.findByAadharNo(customer.getAadharNo())).thenReturn(customer);
		customerService.updateCustomer(customer);
		verify(customerRepo, times(1)).save(customer);
	}
	
	@Test		//Test for Find Customer By Customer Id
	void findCustomerByIdTest() {
		
		Mockito.when(customerRepo.findCustomerById(customer.getId())).thenReturn(customer);
		customerService.findCustomerById(customer.getId());
		verify(customerRepo, times(1)).findCustomerById(customer.getId());
	}
	
	@Test	//Test for Find Customer By Account Number
	void findCustomerByAccNoTest() {
		
		Set<Account> accounts = new HashSet<>();
		accounts.add(account);
		customer.setAccounts(accounts);
		
		Mockito.when(accountRepo.findByAccountNumber(account.getAccountNumber())).thenReturn(account);
		Mockito.when(customerRepo.findAll()).thenReturn(List.of(customer));
	
		Assertions.assertEquals(List.of(customer), customerService.findCustomerByAccNo(account.getAccountNumber()).getBody());
	}
	
	@Test	//Test for Add Account to Customer
	void addAccountTest() {
		
		Mockito.when(customerRepo.findById(Optional.of(customer).get().getId())).thenReturn(Optional.of(customer));
		Mockito.when(accountRepo.findById(Optional.of(account).get().getId())).thenReturn(Optional.of(account));
		Mockito.when(customerRepo.findCustomerById(customer.getId())).thenReturn(customer);
		
		customerService.addAccount(customer.getId(), account.getId());
		verify(customerRepo, times(1)).save(customer);
	}
	
	@Test	//Test for Remove Account from Customer
	void removeAccountTest() {
		
		customer.getAccounts().add(account);
		
		Mockito.when(customerRepo.findById(Optional.of(customer).get().getId())).thenReturn(Optional.of(customer));
		Mockito.when(accountRepo.findById(Optional.of(account).get().getId())).thenReturn(Optional.of(account));
		
		customerService.removeAccount(customer.getId(), account.getId());
		verify(customerRepo, times(1)).save(customer);
	}
	
	@Test		//Test for All Customer List
	void findAllCustomers() {
		
		Mockito.when(customerRepo.findAll()).thenReturn(List.of(customer));
		Assertions.assertEquals(List.of(customer), customerService.findAllCustomers().getBody());
	}
	
	@Test	//Test for Delete Customer By Id
	void deleteCustomerByIdTest() {
		
		Mockito.when(customerRepo.existsById(1L)).thenReturn(true);
		customerService.deleteCustomerById(1L);
		verify(customerRepo, times(1)).deleteById(1L);
	}
	
	@Test	//To Find Customer Name By Account Number
	void customerNameByAccNoTest() {

		customer.getAccounts().add(account);
		Mockito.when(accountRepo.findByAccountNumber(account.getAccountNumber())).thenReturn(account);
		Mockito.when(customerRepo.findAll()).thenReturn(List.of(customer));
		Assertions.assertEquals(List.of("test").toString(), customerService.customerNameByAccNo(account.getAccountNumber()));
	}

}
