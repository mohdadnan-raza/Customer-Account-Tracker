package cat.foundation.bank.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Customer Controller
 * 
 * @author MO20364776
 *
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService;
	
	//Create New Customer
	@PostMapping("/create")
	public ResponseEntity<Object> createCustomer(@RequestBody CustomerDTO customerDto){
		Customer customer = customerDto.convertToEntity();
		return customerService.createCustomer(customer);
	}
	
	//Update Existing Customer
	@PutMapping("/update")
	public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDTO customerDto){
		Customer customer = customerDto.convertToEntity();
		return customerService.updateCustomer(customer);
	}
	
	//Find Customer by Customer Id
	@GetMapping("/id/{customerId}")
	public ResponseEntity<Object> findCustomerById(@PathVariable long customerId){
		return customerService.findCustomerById(customerId);
	}
	
	//Find Customer by Account Number
	@GetMapping("/accountNumber/{accountNo}")
	public ResponseEntity<Object> findCustomerByAccNo(@PathVariable long accountNo){
		return customerService.findCustomerByAccNo(accountNo);
	}

	//List of Customers
	@GetMapping("/list")
	public ResponseEntity<Object> findAllCustomers(){
		return customerService.findAllCustomers();
	}
	
	//Delete Customer by Customer Id
	@DeleteMapping("/delete/id/{id}")
	public ResponseEntity<Object> deleteCustomerById(@PathVariable long id){
		return customerService.deleteCustomerById(id);
	}
	
	//Add Account
	@PutMapping(path="/{customerId}/addAccount/{accountId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addAccount(@PathVariable Long customerId, @PathVariable Long accountId) {
        return customerService.addAccount(customerId, accountId);
    }
	
	//Remove Account
	@PutMapping("/{customerId}/removeAccount/{accountId}")
    public ResponseEntity<Object> removeAccount(@PathVariable Long customerId, @PathVariable Long accountId) {
		return customerService.removeAccount(customerId, accountId);
    }

}
