package org.example.bankproject.debitCard.jpa;

import jakarta.persistence.*;
import lombok.*;
import org.example.bankproject.cardStatus.CardStatus;
import org.example.bankproject.account.jpa.Account;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebitCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false, unique = true, length = 16)
    private String debitCardNumber;

    @Column(nullable = false)
    private String pinCode;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private String ccvCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus cardStatus;

    @Column(nullable = false)
    private BigDecimal dailyTransactionLimit;

    @Column(nullable = false)
    private BigDecimal dailyWithdrawalLimit;
}
