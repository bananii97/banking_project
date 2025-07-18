package org.example.bankproject.account.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.bankproject.user.jpa.Person;
import org.hibernate.annotations.CreationTimestamp;

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
    @JsonBackReference
    private Person person;

    private String accountNumber;
    private boolean primaryAccount;
    private BigDecimal balance;
    private boolean isActive = true;
    private boolean softDeleted;

    @CreationTimestamp
    private LocalDate accountOpenedAt;

    private static final String bicNumber = "BREXPLPWXXX";
}
