package mmitina.bank.Controllers;

import lombok.RequiredArgsConstructor;
import mmitina.bank.DTO.request.PutMoneyRequest;
import mmitina.bank.DTO.request.TransactionRequest;
import mmitina.bank.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/new")
    public ResponseEntity<Void> makeTransaction(@CookieValue(name = "jwt") String jwt,
                                                @RequestBody TransactionRequest transactionRequest){
        return transactionService.makeTransaction(jwt, transactionRequest);
    }
}
