package mmitina.bank.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="clients")
@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="email")
    private String email;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="password")
    private String password;
}
