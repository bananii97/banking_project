package org.example.bankproject.user.jpa;

import jakarta.persistence.*;
import lombok.*;
import org.example.bankproject.account.jpa.Account;
import org.example.bankproject.address.model.Address;
import org.example.bankproject.gender.Gender;
import org.example.bankproject.identityCard.model.IdentityCard;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "person")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String email;
    private String nationalIdentityNumber;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    private IdentityCard identityCard;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Account> accounts;

    private int phoneNumber;
}
