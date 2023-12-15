package mmitina.bank.service;

import mmitina.bank.DTO.request.TransactionRequest;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    boolean isPosible(Double balance, Double money);
    ResponseEntity<Void> makeTransaction(String jwt, TransactionRequest transactionRequest);
}
