package org.example.bankproject.user.verification;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.bankproject.personal_data.Gender;
import org.example.bankproject.personal_data.dto.PersonalDataDto;

import java.time.format.DateTimeFormatter;

public class NationalNumberValidator implements ConstraintValidator<ValidNationalNumber, PersonalDataDto> {

    @Override
    public void initialize(ValidNationalNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PersonalDataDto person, ConstraintValidatorContext constraintValidatorContext) {
        return genderMatchesIdentificationNumber(person)
                && identificationNumberStartsWithDateOfBirth(person)
                && identificationNumberWeightIsCorrect(person);
    }

    private boolean genderMatchesIdentificationNumber(PersonalDataDto person) {
        if (Integer.parseInt(person.getNationalIdentityNumber()
                .substring(9, 10)) % 2 == 0) {
            return Gender.FEMALE.equals(person.getGender());
        } else {
            return Gender.MALE.equals(person.getGender());
        }
    }

    private boolean identificationNumberStartsWithDateOfBirth(PersonalDataDto person) {
        String dateOfBirthString = person.getDateOfBirth()
                .format(DateTimeFormatter.ofPattern("yyMMdd"));
        if (dateOfBirthString.charAt(0) == '0') {
            int monthNum = Integer.parseInt(dateOfBirthString.substring(2, 4));
            monthNum += 20;
            dateOfBirthString = dateOfBirthString
                    .substring(0, 2) + monthNum + dateOfBirthString.substring(4, 6);
        }
        return dateOfBirthString
                .equals(person.getNationalIdentityNumber().substring(0, 6));
    }

    private boolean identificationNumberWeightIsCorrect(PersonalDataDto person) {
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

        int weightSum = 0;
        for (int i = 0; i < 10; i++) {
            weightSum += Integer.parseInt(person.getNationalIdentityNumber()
                    .substring(i, i + 1)) * weights[i];
        }

        int actualSum = 10 - weightSum % 10;

        int checkSum = Integer
                .parseInt(person.getNationalIdentityNumber().substring(10, 11));

        return actualSum == checkSum;
    }
}
