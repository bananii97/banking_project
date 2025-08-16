package org.example.bankproject.user.user_service;

import jakarta.persistence.EntityNotFoundException;
import org.example.bankproject.address.model.Address;
import org.example.bankproject.gender.Gender;
import org.example.bankproject.identityCard.model.IdentityCard;
import org.example.bankproject.user.PersonMapper;
import org.example.bankproject.user.PersonService;
import org.example.bankproject.user.api.PersonDto;
import org.example.bankproject.user.jpa.Person;
import org.example.bankproject.user.jpa.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Captor
    private ArgumentCaptor<Person> personArgumentCaptor;

    private static Person person;

    @BeforeAll
    public static void setup() {
        person = Person.builder()
                .name("Agnieszka")
                .lastName("Kowalczyk")
                .email("a.kowaslczyk@example.com")
                .nationalIdentityNumber("92030512345")
                .dateOfBirth(LocalDate.parse("1992-03-05"))
                .gender(Gender.FEMALE)
                .phoneNumber("501214567")
                .identityCard(IdentityCard.builder()
                        .identityCardNumber("ABC123454")
                        .issuePlace("Warszawa")
                        .issuingAuthority("Urząd Dzielnicy Śródmieście")
                        .validUntil(LocalDate.parse("2030-12-31"))
                        .build()
                )
                .address(Address.builder()
                        .street("ul.Stara")
                        .apartmentNumber("15A")
                        .city("Bialystok")
                        .postalCode("00-789")
                        .build()
                )
                .build();
    }

    @Test
    void shouldSaveNewPerson_whenValidPersonDtoProvided() {
        //given
        when(personRepository.save(any(Person.class))).thenAnswer(args -> {
            Person person = args.getArgument(0);
            return person;
        });

        //when
        PersonDto returned = personService.createPerson(PersonMapper.toDto(person));

        //then
        verify(personRepository).save(personArgumentCaptor.capture());
        Person createdPerson = personArgumentCaptor.getValue();

        verify(personRepository, times(1)).save(any(Person.class));

        assertThat(PersonMapper.fromDto(returned))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(createdPerson);
    }

    @Test
    void shouldReturnPerson_whenPersonExists() {
        //given
        Long personId = 1L;
        Person person = new Person();
        person.setId(personId);
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        //when
        Person returned = personService.findByPersonId(personId);

        //then
        verify(personRepository, times(1)).findById(personId);
        assertEquals(returned.getId(), person.getId());
    }

    @Test
    void shouldThrowEntityNotFoundException_whenPersonDoesNotExist() {
        //given
        Long personId = 1L;
        String message = "Person with id: " + personId + " not found";
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        //when then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> personService.findByPersonId(personId))
                .withMessage(message);
        verify(personRepository, times(1)).findById(personId);
    }

    @Test
    void shouldUpdatePersonalData() {
        //given
        Long personId = 1L;
        PersonDto personDto = PersonDto.builder()
                .email("adres@example.com")
                .phoneNumber("908098890")
                .build();

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(personRepository.save(personArgumentCaptor.capture())).thenAnswer(args -> {
            Person updatedPerson = args.getArgument(0);
            return updatedPerson;
        });

        //when
        PersonDto returnedPerson = personService.updatePersonalData(personId, personDto);

        //then
        verify(personRepository).save(personArgumentCaptor.capture());
        Person createdPerson = personArgumentCaptor.getValue();
        verify(personRepository, times(1)).findById(personId);

        assertThat(PersonMapper.fromDto(returnedPerson))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(createdPerson);
    }

    @Test
    void shouldReturnPersonDto_whenPersonExists() {
        //given
        Long personId = 1L;
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        //when
        PersonDto returned = personService.getPersonalData(personId);

        //then
        verify(personRepository, times(1)).findById(personId);
        assertThat(PersonMapper.fromDto(returned))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(person);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenPersonDoesNotExistForGivenId() {
        //given
        Long personId = 1L;
        String message = "Person with id: " + personId + " not found";
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        //when then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> personService.getPersonalData(personId))
                .withMessage(message);

        verify(personRepository, times(1)).findById(personId);
    }
}
