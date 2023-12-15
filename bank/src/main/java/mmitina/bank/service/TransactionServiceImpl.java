package mmitina.bank.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmitina.bank.DAO.AccountDAO;
import mmitina.bank.DAO.ClientDAO;
import mmitina.bank.DAO.TransactionDAO;
import mmitina.bank.DTO.request.TransactionRequest;
import mmitina.bank.entities.Account;
import mmitina.bank.entities.Transaction;
import mmitina.bank.security.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionDAO transactionDAO;
    private final AccountDAO accountDAO;
    private final ClientDAO clientDAO;
    private final JwtService jwtService;

    @Override
    public boolean isPosible(Double accountBalance, Double requiredMoney) {
        return accountBalance > requiredMoney;
    }

    @Override
    public ResponseEntity<Void> makeTransaction(String jwt, TransactionRequest transactionRequest) {
        Account sourceAccount = accountDAO.getAccountOfClient(clientDAO.findByEmail(jwtService.parseToken(jwt).getEmail()));
        if (isPosible(sourceAccount.getBalance(), transactionRequest.amount)){
            Account targetAccount = accountDAO.getAccountOfClient(clientDAO.findByEmail(transactionRequest.destination));
            Double newSourceBalance = sourceAccount.getBalance() - transactionRequest.amount;
            Double newTargetBalance = targetAccount.getBalance() + transactionRequest.amount;
            accountDAO.putMoney(newSourceBalance, sourceAccount.getId());
            accountDAO.putMoney(newTargetBalance, targetAccount.getId());
            Transaction transaction = Transaction.builder()
                    .amount(transactionRequest.amount)
                    .date(LocalDateTime.now())
                    .sourceAccount(sourceAccount)
                    .destinationAccount(targetAccount)
                    .build();
            transactionDAO.save(transaction);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }
}
