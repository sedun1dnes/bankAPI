package mmitina.bank.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmitina.bank.DTO.request.PutMoneyRequest;
import mmitina.bank.DTO.response.AccountDataResponse;
import mmitina.bank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/new")
    public ResponseEntity<Void> createAccount(@CookieValue(name = "jwt") String jwt){
        return accountService.createAccount(jwt);
    }
    @GetMapping("/my")
    public AccountDataResponse getAccount(@CookieValue(name = "jwt") String jwt){
        return accountService.getAccount(jwt);
    }
    @PostMapping("/put")
    public ResponseEntity<Void> putMoney(@CookieValue(name = "jwt") String jwt,
                                         @RequestBody PutMoneyRequest putMoneyRequest){
        return accountService.putMoney(jwt, putMoneyRequest);
    }
}
