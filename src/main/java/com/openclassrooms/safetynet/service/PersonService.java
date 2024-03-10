package com.openclassrooms.safetynet.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;

@Service
public class PersonService {

	private static final Logger logger = LogManager.getLogger("PersonService");

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private MedicalRecordService medicalRecordService;

	public Person createPerson(Person person) {
		logger.info("Request Post on person done");
		return personRepository.save(person);
	}

	/**
	 * public Person getPerson(String firstName, String lastName) { return
	 * personRepository.findByFirstNameAndLastName(firstName, lastName); }
	 */
	public List<Person> getAllPersons() {
		logger.info("Request Get all on person done");
		return personRepository.findAll();
	}

	public Person updatePerson(Person person) {
		logger.info("Request Put on person done");
		return personRepository.update(person.getFirstName(), person.getLastName(), person);
	}

	public void deletePerson(String firstName, String lastName) {
		logger.info("Request Delete on person done");
		personRepository.delete(firstName, lastName);
		medicalRecordService.deleteMedicalRecordForDeletedPerson(firstName, lastName);
	}
}
