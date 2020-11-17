package com.nauta.voyager.service;

import com.nauta.voyager.service.exception.PersonNotFoundException;
import com.nauta.voyager.entity.Person;
import com.nauta.voyager.repository.PersonRepository;
import java.util.List;
import javax.transaction.Transactional;

public class PersonService {
    
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }
    
    public Person getPersonById(Long personId) {
        Person p = personRepository.findById(personId);
        if (p == null) {
            throw new PersonNotFoundException();
        }
        return p;
    }
    
    @Transactional
    public void createPerson(Person p) {
        personRepository.save(p);
    }
    
    @Transactional
    public Person updatePerson(Person p) {
        return personRepository.update(p);
    }
    
    @Transactional
    public boolean deletePerson(Long personId) {
        personRepository.delete(personId);
        return !personRepository.exists(personId);
    }       
}
    