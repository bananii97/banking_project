package org.example.bankproject.personal_data;


import org.example.bankproject.personal_data.dto.PersonalDataDto;
import org.example.bankproject.personal_data.model.PersonalData;

public class PersonalDataMapper {

    public static PersonalDataDto toDto(PersonalData personalData) {
        return PersonalDataDto.builder()
                .nationalIdentityNumber(personalData.getNationalIdentityNumber())
                .dateOfBirth(personalData.getDateOfBirth())
                .gender(personalData.getGender())
                .age(personalData.getAge())
                .build();
    }

    public static PersonalData fromDto(PersonalDataDto personalDataDto) {
        return PersonalData.builder()
                .nationalIdentityNumber(personalDataDto.getNationalIdentityNumber())
                .dateOfBirth(personalDataDto.getDateOfBirth())
                .gender(personalDataDto.getGender())
                .age(personalDataDto.getAge())
                .build();
    }
}
