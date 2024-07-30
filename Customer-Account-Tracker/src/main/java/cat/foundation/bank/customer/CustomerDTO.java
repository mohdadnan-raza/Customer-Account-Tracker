package cat.foundation.bank.customer;

import java.util.HashSet;
import java.util.Set;

import cat.foundation.bank.account.Account;
import lombok.Data;

@Data
public class CustomerDTO {

	private long id;

	private String name;

	private String email;

	private long aadharNo;

	private long contact;

	private Set<Account> accounts = new HashSet<>();
	
	
	//Convert DTO to Entity
	public Customer convertToEntity() {
		Customer customer = new Customer();
		customer.setId(this.id);
		customer.setName(this.name);
		customer.setEmail(this.email);
		customer.setAadharNo(this.aadharNo);
		customer.setContact(this.contact);
		customer.setAccounts(this.accounts);
		return customer;
	}
}
