package mmitina.bank.repositories;

import mmitina.bank.entities.Account;
import mmitina.bank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findTransactionsBySourceAccount(Account sourceAccount);
    List<Transaction> findTransactionsByDestinationAccount(Account destinationAccount);
}
