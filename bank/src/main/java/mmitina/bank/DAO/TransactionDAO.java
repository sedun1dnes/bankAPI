package mmitina.bank.DAO;

import lombok.RequiredArgsConstructor;
import mmitina.bank.entities.Account;
import mmitina.bank.entities.Client;
import mmitina.bank.entities.Transaction;
import mmitina.bank.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDAO {

    private final TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }
    public Transaction findById(Integer transactionId){
        return transactionRepository.findById(transactionId)
                .orElse(null);
    }
    public List<Transaction> findTransactionBySource(Account account){
        return transactionRepository.findTransactionsBySourceAccount(account);
    }
    public List<Transaction> findTransactionByDestination(Account account){
        return transactionRepository.findTransactionsByDestinationAccount(account);
    }
    public void deleteById(Integer id){
        transactionRepository.deleteById(id);
    }
}
