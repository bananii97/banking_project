package org.example.bankproject.user.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.bankproject.user.model.Address;
import org.example.bankproject.user.model.PersonalData;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "person")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private PersonalData personalData;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private int phoneNumber;
}
