package org.example.bankproject.account.jpa;


import jakarta.persistence.*;
import lombok.*;
import org.example.bankproject.user.jpa.Person;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="person_id")
    private Person person;

    private String accountNumber;
    private String bicNumber;
    private LocalDate dateOpenAccount;
    private BigDecimal balance;
    private boolean primaryAccount;
    private boolean isActive;
    private boolean softDeleted;
}
