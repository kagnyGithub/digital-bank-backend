package sid.org.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sid.org.dto.CustomerDTO;
import sid.org.exception.CustumerNotFoundExeption;
import sid.org.services.AccountService;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    AccountService accountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customerDTOList(){
        return accountService.getAllCustomers();
    }
    @GetMapping("/searchCustomers")
    public List<CustomerDTO> searchCustomers(@RequestParam("kw") String keyword){
        return accountService.searchCustomers("%"+keyword+"%");
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomerDTO(@PathVariable String id) throws CustumerNotFoundExeption {
        return accountService.getGustomserById(id);
    }

    @PostMapping("/customers")
    public  CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return accountService.addCustomer(customerDTO);
    }

    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable String id) throws CustumerNotFoundExeption {
        return accountService.udateCustomer(customerDTO,id);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable String id){
        accountService.deleteCustomer(id);
    }
}
