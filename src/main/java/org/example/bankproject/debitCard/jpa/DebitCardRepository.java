package org.example.bankproject.debitCard.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {

    boolean existsByCardNumber(String cardNumber);
}
