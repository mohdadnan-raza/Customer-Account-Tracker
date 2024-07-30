package cat.foundation.bank.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="Account")
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Account_Id")
	private long id;
	
	@Column(name="Account_Number")
	private long accountNumber;
	
	@Column(name="Account_Type")
	private String accountType;
	
	@Column(name="Balance_Amount")
	private double balanceAmount;

}
