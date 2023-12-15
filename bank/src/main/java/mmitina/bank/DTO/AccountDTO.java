package mmitina.bank.DTO;

import lombok.Data;

@Data
public class AccountDTO {
    private int id;
    private Integer hostClientId;
    private double balance;
}
