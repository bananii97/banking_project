package org.example.bankproject.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bankproject.user.PersonService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/create")
    public PersonDto createPerson(@RequestBody @Valid PersonDto personDto) {
        return personService.createPerson(personDto);
    }

    @GetMapping("/{id}")
    public PersonDto getPersonData(@PathVariable Long id) {
        return personService.getPersonalData(id);
    }

    @PatchMapping("/{id}")
    public PersonDto updatePersonData(@PathVariable Long id, @RequestBody @Valid PersonDto personDto) {
        return personService.updatePersonalData(id, personDto);
    }
}
