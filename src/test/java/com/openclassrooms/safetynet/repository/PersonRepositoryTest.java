package com.openclassrooms.safetynet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.safetynet.model.Person;

class PersonRepositoryTest {

	private PersonRepository personRepository;

	@BeforeEach
	void setUp() {
		personRepository = new PersonRepository();
		PersonRepository
				.initializePersons(Arrays.asList(new Person("John", "Doe", "address1", "City", "Zip", "Phone", "Email"),
						new Person("Jane", "Doe", "address1", "City", "Zip", "Phone", "Email")));
	}

	@Test
	void saveAndFindByFirstNameAndLastNameTest() {
		// Given
		Person newPerson = new Person("John", "Snow", "address2", "City", "Zip", "Phone", "Email");
		personRepository.save(newPerson);
		// When
		Person foundPerson = personRepository.findByFirstNameAndLastName("John", "Snow");
		// Then
		assertEquals(newPerson, foundPerson);
	}

	@Test
	void findAllTest() {
		// Given & When
		List<Person> persons = personRepository.findAll();
		// Then
		assertTrue(persons.size() >= 2);
	}

	@Test
	void updateTest() {
		// Given
		Person updatedPerson = new Person("John", "Doe", "address3", "New City", "New Zip", "New Phone", "New Email");
		// When
		Person result = personRepository.update("John", "Doe", updatedPerson);
		// Then
		assertNotNull(result);
		assertEquals("New City", result.getCity());
		assertEquals("address3", result.getAddress());
	}

	@Test
	void deleteTest() {
		// Given & When
		personRepository.delete("Jane", "Doe");
		// Then
		assertNull(personRepository.findByFirstNameAndLastName("Jane", "Doe"));
	}

	@Test
	void findByCityTest() {
		// Given & When
		List<Person> personsByCity = personRepository.findByCity("City");
		// Then
		assertFalse(personsByCity.isEmpty());
	}

	@Test
	void findByAddressTest() {
		// Given & When
		List<Person> personsByAddress = personRepository.findByAddress("address1");
		// Then
		assertFalse(personsByAddress.isEmpty());
	}

	@Test
	void findByLastNameTest() {
		// Given & When
		List<Person> personsByLastName = personRepository.findByLastName("Doe");
		// Then
		assertTrue(personsByLastName.size() >= 2);
	}
}
