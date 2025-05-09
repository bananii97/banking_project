package org.example.bankproject.user.verification;

import org.example.bankproject.user.api.PersonDto;
import org.example.bankproject.user.model.Gender;

import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

public class NationalNumberIdentification implements Verification {

    @Override
    public boolean passed(PersonDto person) {
        return genderMatchesIdentificationNumber(person)
                && identificationNumberStartsWithDateOfBirth(person)
                && identificationNumberWeightIsCorrect(person);
    }

    private boolean genderMatchesIdentificationNumber(PersonDto person) {
        char[] pesel = person.getPersonalData().getNationalIdentityNumber();
        if (Character.getNumericValue(pesel[9]) % 2 == 0) {
            return Gender.FEMALE.equals(person.getPersonalData().getGender());
        } else {
            return Gender.MALE.equals(person.getPersonalData().getGender());
        }
    }

    private boolean identificationNumberStartsWithDateOfBirth(PersonDto person) {
        String dateOfBirthString = person.getPersonalData().getDateOfBirth()
                .format(DateTimeFormatter.ofPattern("yyMMdd"));

        if (dateOfBirthString.charAt(0) == '0') {
            int month = Integer.parseInt(dateOfBirthString.substring(2, 4));
            month += 20;
            String modifiedMonth = format("%02d", month);
            dateOfBirthString = dateOfBirthString.substring(0, 2)
                    + modifiedMonth
                    + dateOfBirthString.substring(4, 6);
        }

        // porównanie 6 pierwszych cyfr PESEL
        String peselDatePart = new String(person.getPersonalData().getNationalIdentityNumber(), 0, 6);
        return dateOfBirthString.equals(peselDatePart);
    }

    private boolean identificationNumberWeightIsCorrect(PersonDto person) {
        char[] pesel = person.getPersonalData().getNationalIdentityNumber();
        int[] weights = {1, 2, 7, 8, 1, 3, 7, 9, 1, 3};

        if (pesel.length != 11) {
            return false;
        }

        int weightSum = 0;
        for (int i = 0; i < 10; i++) {
            int digit = Character.getNumericValue(pesel[i]);
            weightSum += digit * weights[i];
        }

        int actualSum = (10 - weightSum % 10) % 10;
        int checkSum = Character.getNumericValue(pesel[10]);

        return actualSum == checkSum;
    }
}
