package org.example.bankproject.personal_data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.bankproject.personal_data.Gender;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PersonalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nationalIdentityNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private int age;
}


