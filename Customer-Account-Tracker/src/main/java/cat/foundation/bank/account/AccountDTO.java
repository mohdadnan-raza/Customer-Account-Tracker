package cat.foundation.bank.account;

import lombok.Data;

@Data
public class AccountDTO {
	
	private long id;
	private long accountNumber;
	private String accountType;
	private double balanceAmount;
	
	
	
	//To Convert DTO to Entity
	public Account convertToEntity() {
		Account account = new Account();
		account.setId(this.id);
		account.setAccountNumber(this.accountNumber);
		account.setAccountType(this.accountType);
		account.setBalanceAmount(this.balanceAmount);
		return account;
	}

}
