package com.openclassrooms.safetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private MedicalRecordService medicalRecordService;

	public Person createPerson(Person person) {
		return personRepository.save(person);
	}

	public Person getPerson(String firstName, String lastName) {
		return personRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	public List<Person> getAllPersons() {
		return personRepository.findAll();
	}

	public Person updatePerson(Person person) {
		return personRepository.update(person.getFirstName(), person.getLastName(), person);
	}

	public void deletePerson(String firstName, String lastName) {
		personRepository.delete(firstName, lastName);
		medicalRecordService.deleteMedicalRecordForDeletedPerson(firstName, lastName);
	}
}
