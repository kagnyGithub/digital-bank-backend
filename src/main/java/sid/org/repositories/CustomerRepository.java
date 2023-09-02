package sid.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sid.org.entities.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {
    @Query("select c from Customer c where c.name like :kw")
    List<Customer> searchCustomers(@Param("kw") String keyword);

}
