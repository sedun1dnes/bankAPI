package mmitina.bank.service;

import mmitina.bank.DTO.request.PutMoneyRequest;
import mmitina.bank.DTO.response.AccountDataResponse;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<Void> createAccount(String jwt);
    AccountDataResponse getAccount(String jwt);
    ResponseEntity<Void> putMoney(String jwt, PutMoneyRequest putMoneyRequest);
}
