package cat.foundation.bank.customer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cat.foundation.bank.account.Account;
import cat.foundation.bank.account.AccountRepository;
import cat.foundation.bank.account.AccountService;
import cat.foundation.bank.exception.AlreadyExists;
import cat.foundation.bank.exception.NotExists;
import cat.foundation.bank.exception.ResourceNotFoundException;
import cat.foundation.bank.exception.SomethingWentWrongException;

/**
 * Customer Service
 * 
 * @author MO20364776
 *
 */
@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	AccountRepository accountRepo;

	@Autowired
	AccountService accountService;
	
	/**
	 * Create New Customer
	 * 
	 * @param customer
	 * @return
	 */
	public ResponseEntity<Object> createCustomer(Customer customer) {
			//if customer already exists with same Aadhar Number then throw exception
		if (customerRepo.existsByAadharNo(customer.getAadharNo()))
			throw new AlreadyExists("Customer already Exists with Aadhar Number: " + customer.getAadharNo());

		else { // If customer not exists already then Save the Customer and confirm

			customer.getAccounts().stream().forEach(account->accountService.createAccount(account));
			customerRepo.save(customer);

			// If customer Added successfully then confirm
			if (customerRepo.existsById(customer.getId()))
				return new ResponseEntity<>(customer+"\nCustomer is Added", HttpStatus.OK);

			// If customer is Not Added then throw exception
			else
				throw new SomethingWentWrongException("Customer is Not Added");
		}
	}

	
	/**
	 * Update Existing Customer Details
	 * 
	 * @param newCustomerDetails
	 * @return
	 */
	public ResponseEntity<Object> updateCustomer(Customer newCustomerDetails) {

		// find customer for given details, throw exception if Not found
		Customer oldCustomerDetails = Optional.ofNullable(customerRepo.findByAadharNo(newCustomerDetails.getAadharNo()))
				.orElseThrow(() -> new ResourceNotFoundException("Customer Not Found with given details"));

		//set previously added accounts and save customer
		newCustomerDetails.getAccounts().stream().forEach(account->accountService.updateAccount(account));
		newCustomerDetails.setId(oldCustomerDetails.getId());
		newCustomerDetails.setAadharNo(oldCustomerDetails.getAadharNo());
		customerRepo.save(newCustomerDetails);

		// check if customer updated then confirm else throw exception
		if (customerRepo.findByAadharNo(oldCustomerDetails.getAadharNo()).equals(newCustomerDetails))
			return new ResponseEntity<>(newCustomerDetails+"\nCustomer is Updated", HttpStatus.OK);

		else
			throw new SomethingWentWrongException("Customer is Not Updated");
	}


	/**
	 * Find Customer by Customer_Id
	 * 
	 * @param customerId
	 * @return
	 */
	public ResponseEntity<Object> findCustomerById(Long customerId) {
		// find customer for given id, throw exception if Not found
		Customer customer = Optional.ofNullable(customerRepo.findCustomerById(customerId))
				.orElseThrow(() -> new ResourceNotFoundException("Customer Not Found for Id: " + customerId));

		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	
	/**
	 * Find Customer by Account Number
	 * 
	 * @param accountNo
	 * @return
	 */
	public ResponseEntity<Object> findCustomerByAccNo(long accountNo) {

		// Find Account for given Account Number
		Account account = Optional.ofNullable(accountRepo.findByAccountNumber(accountNo))
				.orElseThrow(() -> new ResourceNotFoundException("Account Not Found for: " + accountNo));

		// Finding customers with above account
		List<Customer> customers = Optional.ofNullable(customerRepo.findAll())
				.orElseThrow(() -> new ResourceNotFoundException("No Customer Found"));
		customers = customers.stream().filter(customer -> customer.getAccounts().contains(account))
				.collect(Collectors.toList());
		// If No Customer found for above Account then throw exception
		if (customers.isEmpty())
			throw new ResourceNotFoundException("Customers Not Found for Account Number " + accountNo);

		// If Customer Found then return and confirm
		else
			return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	
	/**
	 * Add Existing Account
	 * 
	 * @param customerId
	 * @param accountId
	 * @return
	 */
	public ResponseEntity<Object> addAccount(Long customerId, Long accountId) {

		// find customer for given customer id
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer Not Found for customer Id: " + customerId));

		// find account for given account id
		Account account = accountRepo.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account Not Found for account Id: " + accountId));

		// If Same Account Type Already Exists for Customer Then throw exception
		if (customer.getAccounts().stream().map(acc -> acc.getAccountType().toUpperCase())
				.anyMatch(type -> type.equals(account.getAccountType().toUpperCase())))

			throw new SomethingWentWrongException("Account Type already exists for this customer");

		else { // If Account Type NOT Exists for Customer then Add Account and confirm
			customer.getAccounts().add(account);
			customerRepo.save(customer);

			// check if account added to customer then confirm
			if (customerRepo.findCustomerById(customerId).getAccounts().stream().anyMatch(acc -> acc.equals(account)))
				return new ResponseEntity<>("Account Addded", HttpStatus.OK);

			// If account is Not added then throw exception
			else
				throw new SomethingWentWrongException("Something Went Wrong");
		}
	}

	
	/**
	 * Remove Account
	 * 
	 * @param customerId
	 * @param accountId
	 * @return
	 */
	public ResponseEntity<Object> removeAccount(Long customerId, Long accountId) {

		// find customer for given customer id
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer Not Found for customer Id: " + customerId));

		// find account for given account id
		Account account = accountRepo.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account Not Found for account Id: " + accountId));

		// if account is Not present in customer account list then throw exception
		if (customer.getAccounts().stream().map(Account::getId).noneMatch(id -> id.equals(account.getId())))
			throw new NotExists("Account does't exists in customer account list");

		else { // if account is present in customers account list then remove account and
				// confirm
			customer.getAccounts().remove(account);
			customerRepo.save(customer);

			// if account is successfully removed then confirm
			if (customer.getAccounts().stream().map(Account::getAccountNumber)
					.noneMatch(number -> number.equals(account.getAccountNumber())))
				return new ResponseEntity<>("Account Removed", HttpStatus.OK);
			// if account is Not removed then throw exception
			else
				throw new SomethingWentWrongException("Account is Not Removed");
		}
	}

	
	/**
	 * List of All Customers
	 * @return List of Customers
	 */
	public ResponseEntity<Object> findAllCustomers() {

		List<Customer> customers = customerRepo.findAll();

		if (customers.isEmpty())
			throw new ResourceNotFoundException("No Customer Found");

		else
			return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	
	/**
	 * Delete customer by customer_id
	 * 
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> deleteCustomerById(long id) {
		// if customer exists for given id
		if (Boolean.TRUE.equals(customerRepo.existsById(id))) {

			customerRepo.deleteById(id);
			return new ResponseEntity<>("Customer Deleted", HttpStatus.OK);
		}
		// if customer Not exists for given id then throw exception
		else
			throw new ResourceNotFoundException("Customer Not Found");
	}
	

	/**
	 * Customer Name for Account Number
	 * @param accNo
	 * @return
	 */
	public String customerNameByAccNo(long accNo) {
		// find account for given account number
		Account account = accountRepo.findByAccountNumber(accNo);
		// filter customer with given account
		List<Customer> customers = customerRepo.findAll().stream()
				.filter(customer -> customer.getAccounts().contains(account)).collect(Collectors.toList());
		// return names of customers with given account
		return customers.stream().map(Customer::getName).collect(Collectors.toList()).toString();
	}

}
