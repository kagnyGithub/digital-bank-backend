package sid.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sid.org.entities.BankAccount;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    public List<BankAccount> findByCustomerId(String customerId);
}

