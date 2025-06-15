package org.example.bankproject.account.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    boolean existsByIban(String iban);
    Optional<Account> findByIdAndPersonId(Long personId, Long accountId);
}
