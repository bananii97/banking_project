package org.example.bankproject.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class PersonalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private char[] nationalIdentityNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private int age;
}


