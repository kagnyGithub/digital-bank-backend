package sid.org.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sid.org.entities.Operation;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation,Long> {
    public List<Operation> findByBankAccountId(String accountId);
    public Page<Operation> findByBankAccountIdOrderByDateDesc(String accountId, Pageable pageable);

}
