package sid.org.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sid.org.dto.*;
import sid.org.entities.*;

@Component
public class AccountMapper {
    ModelMapper modelMapper=new ModelMapper();
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        return modelMapper.map(customerDTO, Customer.class);
    }
    public CustomerDTO fromCustomer(Customer customer){
        return modelMapper.map(customer, CustomerDTO.class);
    }
    public Operation fromOperationDTO(OperationDTO operationDTO){
        return modelMapper.map(operationDTO, Operation.class);
    }
    public OperationDTO fromOperation(Operation operation){
        return modelMapper.map(operation, OperationDTO.class);
    }
    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO  currentAccountDTO){
        return modelMapper.map(currentAccountDTO, CurrentAccount.class);
    }
    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        return modelMapper.map(currentAccount, CurrentAccountDTO.class);
    }

    public SavingAccount fromSavingAccountDTO(SavingAccountDTO  savingAccountDTO){
        return modelMapper.map(savingAccountDTO, SavingAccount.class);
    }
    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount){
        return modelMapper.map(savingAccount, SavingAccountDTO.class);
    }

    public BankAccountDTO fromBankAccount(BankAccount bankAccount){
        return  modelMapper.map(bankAccount, BankAccountDTO.class);
    }

}
