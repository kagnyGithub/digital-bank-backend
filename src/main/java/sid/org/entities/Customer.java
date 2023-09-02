package sid.org.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts;
}
