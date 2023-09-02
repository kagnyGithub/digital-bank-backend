package sid.org.services;

import org.springframework.stereotype.Service;
import sid.org.dto.*;
import sid.org.entities.Customer;
import sid.org.exception.BankAccountNotFoundExeption;
import sid.org.exception.CustumerNotFoundExeption;
import sid.org.exception.InsufficientBalanceExeption;

import java.util.List;

@Service
public interface AccountService {
    CustomerDTO addCustomer(CustomerDTO customer);
    List<CustomerDTO> getAllCustomers();
    List<CustomerDTO> searchCustomers(String keyword);
    CustomerDTO getGustomserById(String id) throws CustumerNotFoundExeption;
    CustomerDTO udateCustomer(CustomerDTO customerDTO,String id) throws CustumerNotFoundExeption;
    void deleteCustomer(String id);
    CurrentAccountDTO saveCurrentAccount(double initialBalance, double overdraft, String customerId) throws CustumerNotFoundExeption;
    SavingAccountDTO saveSavingAccount(double initialBalance, double interestRate, String customerId) throws CustumerNotFoundExeption;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundExeption, InsufficientBalanceExeption;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundExeption;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundExeption, InsufficientBalanceExeption;
    List<BankAccountDTO> getAccountDtoList();
    BankAccountDTO getBankAccount(String id) throws BankAccountNotFoundExeption;
    HistoryAccountDTO getHistoryAccount(String accountId,int size,int page) throws BankAccountNotFoundExeption;
    List<OperationDTO> getOperationList(String accountId);
    List<BankAccountDTO> getCustomerAccounts(String customerId) throws CustumerNotFoundExeption;

}
