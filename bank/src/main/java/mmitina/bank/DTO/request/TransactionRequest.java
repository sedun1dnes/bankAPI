package mmitina.bank.DTO.request;

import lombok.Data;

@Data
public class TransactionRequest {
    public String destination;
    public Double amount;
}
