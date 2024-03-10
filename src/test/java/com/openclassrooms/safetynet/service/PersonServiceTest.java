package com.openclassrooms.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;

public class PersonServiceTest {

	@Mock
	private PersonRepository personRepository;

	@Mock
	private MedicalRecordService medicalRecordService;

	@InjectMocks
	private PersonService personService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void createPersonTest() {
		// Given
		Person person = new Person("John", "Doe", "address1", "email1", "phone1", "city1", "zip1");
		when(personRepository.save(person)).thenReturn(person);
		// When
		Person created = personService.createPerson(person);
		// Then
		assertEquals(person, created);
		verify(personRepository).save(person);
	}

	@Test
	public void getAllPersonsTest() {
		// Given
		List<Person> persons = Arrays.asList(new Person("John", "Doe", "address1", "email1", "phone1", "city1", "zip1"),
				new Person("Jane", "Doe", "address1", "email1", "phone1", "city1", "zip1"));
		when(personRepository.findAll()).thenReturn(persons);
		// When
		List<Person> result = personService.getAllPersons();
		// Then
		assertEquals(persons, result);
		verify(personRepository).findAll();
	}

	@Test
	public void updatePersonTest() {
		// Given
		Person person = new Person("John", "Doe", "address1", "email1", "phone1", "city1", "zip1");
		when(personRepository.update(person.getFirstName(), person.getLastName(), person)).thenReturn(person);
		// When
		Person updated = personService.updatePerson(person);
		// Then
		assertEquals(person, updated);
		verify(personRepository).update(person.getFirstName(), person.getLastName(), person);
	}

	@Test
	public void deletePersonTest() {
		// Given
		doNothing().when(personRepository).delete("John", "Doe");
		doNothing().when(medicalRecordService).deleteMedicalRecordForDeletedPerson("John", "Doe");
		// When
		personService.deletePerson("John", "Doe");
		// Then
		verify(personRepository).delete("John", "Doe");
		verify(medicalRecordService).deleteMedicalRecordForDeletedPerson("John", "Doe");
	}
}
