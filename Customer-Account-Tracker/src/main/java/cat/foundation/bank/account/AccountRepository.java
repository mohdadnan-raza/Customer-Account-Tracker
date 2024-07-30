package cat.foundation.bank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Account findAccountById(long accountId);

	Account findByAccountNumber(long accountNumber);

	Boolean existsByAccountNumber(long accountNumber);
}
