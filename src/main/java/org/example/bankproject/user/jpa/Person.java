package org.example.bankproject.user.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    private String nationalIdentityNumber;
    private LocalDate birthDate;
    private int gender;
    private int age;
    private int phoneNumber;
}
