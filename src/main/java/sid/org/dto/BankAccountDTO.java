package sid.org.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sid.org.entities.AccountStatus;
import sid.org.entities.Customer;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class BankAccountDTO {
    private String id;
    private Date createdAt;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    private CustomerDTO customer;

}
