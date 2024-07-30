package cat.foundation.bank.customerTests;

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

import cat.foundation.bank.customer.Customer;
import cat.foundation.bank.customer.CustomerController;
import cat.foundation.bank.customer.CustomerDTO;
import cat.foundation.bank.customer.CustomerService;

@SpringBootTest(classes = cat.foundation.bank.CustomerAccountTrackerApplication.class)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTests {

	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerController customerController;

	// dummy customer
	Customer customer = new Customer();
	CustomerDTO customerDto = new CustomerDTO();

	@BeforeEach
	public void setup() {
		customer.setId(1L);
		customer.setName("test");
		customer.setEmail("test@test.com");
		customer.setAadharNo(111111111111L);
		customer.setContact(9876543210L);
		
		customerDto.setId(1L);
		customerDto.setName("test");
		customerDto.setEmail("test@test.com");
		customerDto.setAadharNo(111111111111L);
		customerDto.setContact(9876543210L);
	}

	@Test
	void contextLoads() {
		assertThat(customerController).isNotNull();
	}
	
	@Test		//Test for Create Customer
	void createCustomerTest() {
		customerController.createCustomer(customerDto);
		Customer customer = customerDto.convertToEntity();
		verify(customerService, times(1)).createCustomer(customer);
	}
	
	@Test		//Test for Update Customer Details
	void updateCustomerTest() {
		customerController.updateCustomer(customerDto);
		Customer customer = customerDto.convertToEntity();
		verify(customerService, times(1)).updateCustomer(customer);
	}
	
	@Test		//Test for Find Customer By Customer Id
	void findCustomerByIdTest() {
		customerController.findCustomerById(customer.getId());
		verify(customerService, times(1)).findCustomerById(customer.getId());
	}
	
	@Test	//Test for Find Customer By Account Number
	void findCustomerByAccNoTest() {
		customerController.findCustomerByAccNo(customer.getId());
		verify(customerService, times(1)).findCustomerByAccNo(customer.getId());
	}
	
	@Test		//Test for All Customer List
	void findAllCustomersTest() {
		customerController.findAllCustomers();
		verify(customerService, times(1)).findAllCustomers();
	}
	
	@Test	//Test for Delete Customer By Id
	void deleteCustomerByIdTest() {
		customerController.deleteCustomerById(customer.getId());
		verify(customerService, times(1)).deleteCustomerById(customer.getId());
	}
	
	@Test	//Test for Add Account to Customer
	void addAccountTest() {
		customerController.addAccount(customer.getId(), 1L);
		verify(customerService, times(1)).addAccount(customer.getId(), 1L);
	}
	
	@Test	//Test for Remove Account from Customer
	void removeAccountTest() {
		customerController.removeAccount(customer.getId(), 1L);
		verify(customerService, times(1)).removeAccount(customer.getId(), 1L);
	}
}
