package sid.org.dto;


import lombok.Data;

import java.util.List;

@Data
public class HistoryAccountDTO {
        private String accountId;
        private double balance;
        private int page;
        private int size;
        private int totalPage;
        private List<OperationDTO> operationDTOList;
    }

