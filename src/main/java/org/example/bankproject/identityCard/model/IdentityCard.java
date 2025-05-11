package org.example.bankproject.identityCard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class IdentityCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identityCardNumber;
    private String issuePlace;
    private String issuingAuthority;
    private LocalDate validUntil;
}


