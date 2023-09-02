package sid.org.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sid.org.dto.*;
import sid.org.entities.BankAccount;
import sid.org.exception.BankAccountNotFoundExeption;
import sid.org.exception.CustumerNotFoundExeption;
import sid.org.exception.InsufficientBalanceExeption;
import sid.org.services.AccountService;

import java.util.List;

@RestController
@CrossOrigin("*")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/accounts")
    public List<BankAccountDTO> bankAccountDTOList(){
        return accountService.getAccountDtoList();
    }
    @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id) throws BankAccountNotFoundExeption {
        return accountService.getBankAccount(id);
    }

    @GetMapping("/accounts/operations/{id}")
    List<OperationDTO> operationDTOList(@PathVariable String id){
        return accountService.getOperationList(id);
    }

    @GetMapping("/accounts/history/{id}")
    public HistoryAccountDTO historyList(@PathVariable String id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) throws BankAccountNotFoundExeption {
        return  accountService.getHistoryAccount(id,size,page);
    }

    @PostMapping("/accounts/credit")
   public void credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundExeption {
       accountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());

   }

    @PostMapping("/accounts/debit")
   public  void  debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundExeption, InsufficientBalanceExeption {
        accountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());

   }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferDTO transferDTO) throws BankAccountNotFoundExeption, InsufficientBalanceExeption {
        accountService.transfer(transferDTO.getAccountSource(),transferDTO.getAccountDestination(),transferDTO.getAmount());
    }

    @GetMapping("/customerAccounts/{customerId}")
    List<BankAccountDTO> customerAccounts(@PathVariable String customerId) throws CustumerNotFoundExeption {
        return accountService.getCustomerAccounts(customerId);
    }


}
