package cat.foundation.bank.customer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import cat.foundation.bank.account.Account;
import lombok.Data;

@Data
@Entity
@Table(name="Customer")
public class Customer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Customer_Id")
	private long id;
	
	@Column(name="Customer_Name")
	private String name;
	
	@Column(name="Customer_Email")
	private String email;
	
	@Column(name="Customer_Aadhar_No")
	private long aadharNo;
	
	@Column(name="Customer_Contact")
	private long contact;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Account> accounts = new HashSet<>();

}
