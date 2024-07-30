package cat.foundation.bank.customerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cat.foundation.bank.customer.Customer;
import cat.foundation.bank.customer.CustomerRepository;

@SpringBootTest(classes = cat.foundation.bank.CustomerAccountTrackerApplication.class)
 class CustomerRepositoryTests {
	
	@Autowired
	private CustomerRepository customerRepo;

	@Test				//FIND BY ID
	 void testFindById() {
		Customer customer = getCustomer();
		customerRepo.save(customer);
		Customer result1 = customerRepo.findById(customer.getId()).get();
		Customer result2 = customerRepo.findCustomerById(customer.getId());
		assertEquals(customer.getId(), result1.getId());
		assertEquals(customer.getId(), result2.getId());
	}
	
	@Test				//FIND BY AADHAR NUMBER
	 void testFindByAadharNumber() {
		Customer customer = getCustomer();
		customerRepo.save(customer);
		//Customer savedCustomer = customerRepo.findById(customer.getId()).get();
		Customer result = customerRepo.findByAadharNo(customer.getAadharNo());
		assertEquals(customer.getAadharNo(), result.getAadharNo());
	}

	@Test				//FIND ALL
	void testFindAll() {
		Customer customer = getCustomer();
		customerRepo.save(customer);
		List<Customer> result = new ArrayList<>();
		customerRepo.findAll().forEach(e -> result.add(e));
		assertEquals(1, result.size());
	}

	@Test				//SAVE
	void testSave() {
		Customer customer = getCustomer();
		customerRepo.save(customer);
		Customer found = customerRepo.findById(customer.getId()).get();
		assertEquals(customer.getId(), found.getId());
	}

	@Test				//DELETE BY ID
	void testDeleteById() {
		Customer customer = getCustomer();
		customerRepo.save(customer);
		customerRepo.deleteById(customer.getId());
		List<Customer> result = new ArrayList<>();
		customerRepo.findAll().forEach(e -> result.add(e));
		assertEquals(0, result.size());
	}
	
	@Test				//EXISTS BY AADHAR NUMBER
	 void testExistsByAccountNumber() {
		Customer customer = getCustomer();
		customerRepo.save(customer);
		Customer savedCustomer = customerRepo.findById(customer.getId()).get();
		
		boolean checkById = customerRepo.existsById(customer.getId());
		boolean checkByAadharNumber = customerRepo.existsByAadharNo(savedCustomer.getAadharNo());
		
		assertEquals(checkById, checkByAadharNumber);
	}

	
	//Dummy Customer
	private Customer getCustomer() {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setAadharNo(1234567890L);
		customer.setName("test");
		customer.setEmail("test@test.com");
		customer.setContact(1234567890L);
		return customer;
	}

}
