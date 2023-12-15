package mmitina.bank.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name="accounts")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @JoinColumn
    @ManyToOne
    private Client hostClient;
    @Column
    private double balance;
}
