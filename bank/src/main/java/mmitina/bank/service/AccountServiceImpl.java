package mmitina.bank.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmitina.bank.DAO.AccountDAO;
import mmitina.bank.DAO.ClientDAO;
import mmitina.bank.DTO.ClientDTO;
import mmitina.bank.DTO.request.PutMoneyRequest;
import mmitina.bank.DTO.response.AccountDataResponse;
import mmitina.bank.entities.Account;
import mmitina.bank.security.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{
    private final JwtService jwtService;
    private final AccountDAO accountDAO;
    private final ClientDAO clientDAO;
    @Override
    public ResponseEntity<Void> createAccount(String jwt) {

        ClientDTO clientDTO = jwtService.parseToken(jwt);
        Account account = Account.builder()
                .balance(0)
                .hostClient(clientDAO.findByEmail(clientDTO.getEmail()))
                .build();
        accountDAO.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public AccountDataResponse getAccount(String jwt){
        ClientDTO clientDTO = jwtService.parseToken(jwt);
        Account account = accountDAO.getAccountOfClient(clientDAO.findByEmail(clientDTO.getEmail()));

        return new AccountDataResponse(account.getBalance());
    }
    @Override
    public ResponseEntity<Void> putMoney(String jwt, PutMoneyRequest putMoneyRequest){
        ClientDTO clientDTO = jwtService.parseToken(jwt);
        Account account = accountDAO.getAccountOfClient(clientDAO.findByEmail(clientDTO.getEmail()));
        Double newBalance = account.getBalance() + putMoneyRequest.getAmount();
        log.info(newBalance.toString());
        log.info(account.toString());
        accountDAO.putMoney(newBalance, account.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
