package org.example.bankproject.user.verification;

import org.example.bankproject.gender.Gender;
import org.example.bankproject.user.api.PersonDto;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class NationalNumberValidator  {

    public boolean passed(PersonDto person) {
        return genderMatchesIdentificationNumber(person)
                && identificationNumberStartsWithDateOfBirth(person)
                && identificationNumberWeightIsCorrect(person);
    }

    private boolean genderMatchesIdentificationNumber(PersonDto person) {
        Gender gender;
        if (Integer.parseInt(person.getNationalIdentityNumber()
                .substring(9, 10)) % 2 == 0) {
            gender = Gender.FEMALE;
        } else {
            gender = Gender.MALE;
        }
        return gender == person.getGender();
    }

    private boolean identificationNumberStartsWithDateOfBirth(PersonDto person) {
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

    private boolean identificationNumberWeightIsCorrect(PersonDto person) {
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
