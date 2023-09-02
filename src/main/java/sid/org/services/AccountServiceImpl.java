package sid.org.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sid.org.dto.*;
import sid.org.entities.*;
import sid.org.exception.BankAccountNotFoundExeption;
import sid.org.exception.CustumerNotFoundExeption;
import sid.org.exception.InsufficientBalanceExeption;
import sid.org.mapper.AccountMapper;
import sid.org.repositories.BankAccountRepository;
import sid.org.repositories.CustomerRepository;
import sid.org.repositories.OperationRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private CustomerRepository  customerRepository;
    private BankAccountRepository  bankAccountRepository;
    private OperationRepository  operationRepository;
    private AccountMapper  accountMapper;

    public AccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, OperationRepository operationRepository, AccountMapper accountMapper) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.operationRepository = operationRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customer) {
        customer.setId(UUID.randomUUID().toString());
        return accountMapper.fromCustomer(customerRepository.save(accountMapper.fromCustomerDTO(customer)));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(accountMapper::fromCustomer).collect(Collectors.toList());
    }

    public List<CustomerDTO> searchCustomers(String kw){
        return customerRepository.searchCustomers(kw).stream().map(accountMapper::fromCustomer).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getGustomserById(String id) throws CustumerNotFoundExeption {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer==null) throw new CustumerNotFoundExeption(String.format("Customer %d not found",id));
        return accountMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO udateCustomer(CustomerDTO customerDTO, String id) throws CustumerNotFoundExeption {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer==null) throw new CustumerNotFoundExeption(String.format("Customer %d not found",id));
        return accountMapper.fromCustomer(customerRepository.save(accountMapper.fromCustomerDTO(customerDTO)));
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CurrentAccountDTO saveCurrentAccount(double initialBalance, double overdraft, String customerId) throws CustumerNotFoundExeption {
        Customer  customer = customerRepository.findById(customerId).orElse(null);
        if (customer==null) throw new CustumerNotFoundExeption(String.format("Customer %d not found",customerId));
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setOverDraft(overdraft);
        currentAccount.setBalance(initialBalance);
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setCustomer(customer);
        CurrentAccount savedAccount= bankAccountRepository.save(currentAccount);
        return accountMapper.fromCurrentAccount(savedAccount) ;
    }

    @Override
    public SavingAccountDTO saveSavingAccount(double initialBalance, double interestRate, String  customerId) throws CustumerNotFoundExeption {
        Customer  customer = customerRepository.findById(customerId).orElse(null);
        if (customer==null) throw new CustumerNotFoundExeption(String.format("Customer Id not found"));
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setInterestRate(interestRate);
        savingAccount.setBalance(initialBalance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCustomer(customer);
        SavingAccount savedAccount= bankAccountRepository.save(savingAccount);
        return accountMapper.fromSavingAccount(savedAccount) ;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundExeption, InsufficientBalanceExeption {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount==null) throw new BankAccountNotFoundExeption(String.format("Account Id not found"));
        if (bankAccount.getBalance()-amount<=0) throw new InsufficientBalanceExeption("Votre balance est insuffissant");
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setBankAccount(bankAccount);
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operationRepository.save(operation);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundExeption {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount==null) throw new BankAccountNotFoundExeption(String.format("Account Id not found"));
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setBankAccount(bankAccount);
        operation.setDate(new Date());
        operation.setType(OperationType.CREDIT);
        operationRepository.save(operation);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundExeption, InsufficientBalanceExeption {
        debit(accountIdSource,amount,"Transfer");
        credit(accountIdDestination,amount,"Transfer");

    }

    @Override
    public List<BankAccountDTO> getAccountDtoList() {
        return bankAccountRepository.findAll().stream().map(accountMapper::fromBankAccount).collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String id) throws BankAccountNotFoundExeption {
        BankAccount  bankAccount = bankAccountRepository.findById(id).orElse(null);
        if (bankAccount==null) throw new BankAccountNotFoundExeption(String.format("Account id not found"));
        return accountMapper.fromBankAccount(bankAccount);
    }

    @Override
    public HistoryAccountDTO getHistoryAccount(String accountId, int size, int page) throws BankAccountNotFoundExeption {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount==null)  throw new BankAccountNotFoundExeption(String.format("Account id not found"));
        HistoryAccountDTO historyAccountDTO = new HistoryAccountDTO();
        historyAccountDTO.setAccountId(accountId);
        historyAccountDTO.setBalance(bankAccount.getBalance());
        historyAccountDTO.setPage(page);
        historyAccountDTO.setSize(size);
        Page<Operation> operationDTOList = operationRepository.findByBankAccountIdOrderByDateDesc(accountId, PageRequest.of(page, size));
        historyAccountDTO.setTotalPage(operationDTOList.getTotalPages());
        historyAccountDTO.setOperationDTOList(operationDTOList.stream().map(accountMapper::fromOperation).collect(Collectors.toList()));
        return historyAccountDTO;
    }

    @Override
    public List<OperationDTO> getOperationList(String accountId) {
        return operationRepository.findByBankAccountId(accountId).stream().map(accountMapper::fromOperation).collect(Collectors.toList());
    }

    @Override
    public List<BankAccountDTO> getCustomerAccounts(String customerId) throws CustumerNotFoundExeption {
        Customer  customer = customerRepository.findById(customerId).orElse(null);
        if (customer==null) throw new CustumerNotFoundExeption(String.format("Customer Id not found"));

        return bankAccountRepository.findByCustomerId(customerId).stream().map(accountMapper::fromBankAccount).collect(Collectors.toList());
    }
}
