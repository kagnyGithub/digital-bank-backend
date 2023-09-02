package sid.org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sid.org.entities.BankAccount;

@Data @NoArgsConstructor @AllArgsConstructor
public class SavingAccountDTO extends BankAccount {
    private double interestRate;
}
