package mmitina.bank.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private Double amount;
    @Column
    private LocalDateTime date;
    @JoinColumn
    @ManyToOne
    private Account sourceAccount;
    @JoinColumn
    @ManyToOne
    private Account destinationAccount;
}
