package mmitina.bank.repositories;

import jakarta.transaction.Transactional;
import mmitina.bank.entities.Account;
import mmitina.bank.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByHostClient(Client hostClient);
    @Modifying
    @Transactional
    @Query("update Account acc set acc.balance = ?1 where acc.id = ?2")
    void updateBalance(Double amount, Integer id);
}
