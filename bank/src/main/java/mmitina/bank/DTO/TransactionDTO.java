package mmitina.bank.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDTO {
    private int id;
    private int amount;
    private LocalDate date;
    private Integer sourceAccountId;
    private Integer destinationAccountId;
}
