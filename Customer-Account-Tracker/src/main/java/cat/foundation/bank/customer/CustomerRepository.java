package cat.foundation.bank.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findCustomerById(Long studentId);
	
	Customer findByAadharNo(Long aadharNo);

	boolean existsByAadharNo(long aadharNo);

}
