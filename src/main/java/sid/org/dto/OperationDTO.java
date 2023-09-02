package sid.org.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sid.org.entities.OperationType;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class OperationDTO {
    private Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
}
