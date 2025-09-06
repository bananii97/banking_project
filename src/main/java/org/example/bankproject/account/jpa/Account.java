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

    public static final String bicNumber = "BREXPLPWXXX";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="person_id")
    private Person person;

    private String accountNumber;
    private boolean primaryAccount;
    private boolean softDeleted;

    @Builder.Default
    private boolean isActive = true;

    @Builder.Default
    private BigDecimal balance = new  BigDecimal(0);

    @Builder.Default
    private LocalDate accountOpenedAt = LocalDate.now();
}
