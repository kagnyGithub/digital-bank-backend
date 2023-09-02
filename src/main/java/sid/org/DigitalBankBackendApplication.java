package sid.org;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sid.org.dto.CustomerDTO;
import sid.org.exception.BankAccountNotFoundExeption;
import sid.org.exception.CustumerNotFoundExeption;
import sid.org.exception.InsufficientBalanceExeption;
import sid.org.services.AccountService;

@SpringBootApplication
public class DigitalBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankBackendApplication.class, args);
	}
	@Bean
	CommandLineRunner start(AccountService accountService){
		return args -> {
			accountService.addCustomer(new CustomerDTO(null,"Aliou","aliou@gmail.com"));
			accountService.addCustomer(new CustomerDTO(null,"Assane","assane@gmail.com"));
			accountService.addCustomer(new CustomerDTO(null,"Mame","mame@gmail.com"));
			accountService.getAllCustomers().forEach(customerDTO -> {
				try {
					accountService.saveCurrentAccount(4000+Math.random()*10000,2000,customerDTO.getId());
					accountService.saveSavingAccount(4000+Math.random()*10000,1000,customerDTO.getId());
				} catch (CustumerNotFoundExeption e) {
					throw new RuntimeException(e);
				}
			});
			accountService.getAccountDtoList().forEach(bankAccountDTO -> {
				for (int i =0;i<10;i++){
					try {
						accountService.credit(bankAccountDTO.getId(),1000+Math.random()*1000,"Credit le compte");
						accountService.debit(bankAccountDTO.getId(),1000+Math.random()*1000,"Debit de credit" );
					} catch (BankAccountNotFoundExeption | InsufficientBalanceExeption e ) {
						throw new RuntimeException(e);
					}

				}
			});
		};
	}

}
