package mmitina.bank.DAO;

import lombok.RequiredArgsConstructor;
import mmitina.bank.entities.Account;
import mmitina.bank.entities.Client;
import mmitina.bank.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDAO {
    private final AccountRepository accountRepository;
    public Account save(Account account){
        return accountRepository.save(account);
    }
    public Account findById(Integer accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }
    public Account getAccountOfClient(Client client){ return accountRepository.findAccountByHostClient(client); }
    public void deleteById(Integer id){
        accountRepository.deleteById(id);
    }
    public void putMoney(Double amount, Integer id) {accountRepository.updateBalance(amount, id);}
}
