package cat.foundation.bank.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="Transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TransactionId")
	private long id;
	
	@Column(name="BeneficiaryName")
	private String beneficiaryName;
	
	@Column(name="BeneficiaryAccNumber")
	private long beneficiaryAccNumber;
	
	@Column(name="Amount")
	private double amount;
	
	@Column(name="SendersAccNumber")
	private long sendersAccNumber;

}
