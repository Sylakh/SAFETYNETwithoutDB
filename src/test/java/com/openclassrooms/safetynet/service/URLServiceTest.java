package com.openclassrooms.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.safetynet.DTO.ChildAlertResponsDTO;
import com.openclassrooms.safetynet.DTO.CommonDTO;
import com.openclassrooms.safetynet.DTO.FireResponsDTO;
import com.openclassrooms.safetynet.DTO.FloodStationPersonDTO;
import com.openclassrooms.safetynet.DTO.PersonInfoResponsDTO;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

class URLServiceTest {

	@Mock
	private PersonRepository personRepository;

	@Mock
	private FireStationRepository fireStationRepository;

	@Mock
	private MedicalRecordRepository medicalRecordRepository;

	@InjectMocks
	private URLService urlService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Test pour communityEmail
	@Test
	void communityEmailTest() {
		// Given
		when(personRepository.findByCity(anyString()))
				.thenReturn(Arrays.asList(new Person("John", "Doe", "address1", "City", "Zip", "phone1", "email1"),
						new Person("Jane", "Doe", "address2", "City", "Zip", "phone2", "email2")));
		// When
		List<String> emails = urlService.communityEmail("City");
		// Then
		assertEquals(2, emails.size());
		assertTrue(emails.contains("email1"));
		assertTrue(emails.contains("email2"));
	}

	@Test
	void phoneAlertTest() {
		// Given
		when(fireStationRepository.findByStation("1")).thenReturn(Arrays.asList("address1", "address2"));
		when(personRepository.findByAddress("address1"))
				.thenReturn(Arrays.asList(new Person("John", "Doe", "address1", "City", "Zip", "phone1", "email")));
		when(personRepository.findByAddress("address2"))
				.thenReturn(Arrays.asList(new Person("Jane", "Roe", "address2", "City", "Zip", "phone2", "email")));
		// When
		List<String> phones = urlService.phoneAlert("1");
		// Then
		assertEquals(2, phones.size());
		assertTrue(phones.contains("phone1"));
		assertTrue(phones.contains("phone2"));
	}

	@Test
	void childAlertTest() {
		// Given
		when(personRepository.findByAddress("address1"))
				.thenReturn(Arrays.asList(new Person("John", "Doe", "address1", "City", "Zip", "phone1", "email"),
						new Person("Adult", "Member", "address1", "City", "Zip", "phone2", "email")));
		when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(new MedicalRecord("John",
				"Doe", "01/01/2010", Arrays.asList("medication1"), Arrays.asList("allergy1")));
		when(medicalRecordRepository.findByFirstNameAndLastName("Adult", "Member")).thenReturn(new MedicalRecord(
				"Adult", "Member", "01/01/1990", Arrays.asList("medication2"), Arrays.asList("allergy2")));
		// When
		ChildAlertResponsDTO response = urlService.childAlert("address1");
		// Then
		assertNotNull(response);
		assertEquals(1, response.listChild().size());
		assertEquals(1, response.listAdult().size());
		assertEquals("John", response.listChild().get(0).firstName());
		assertEquals("Doe", response.listChild().get(0).lastName());
		assertEquals("Adult", response.listAdult().get(0).firstName());
	}

	@Test
	void fireTest() {
		// Given
		when(fireStationRepository.findByAddress("address1")).thenReturn(new FireStation("address1", "1"));
		when(personRepository.findByAddress("address1"))
				.thenReturn(Arrays.asList(new Person("John", "Doe", "address1", "City", "Zip", "phone", "email")));
		when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(
				new MedicalRecord("John", "Doe", "01/01/1980", Arrays.asList("medication"), Arrays.asList("allergy")));
		// When
		FireResponsDTO response = urlService.fire("address1");
		// Then
		assertNotNull(response);
		assertEquals("l'adresse address1 est couverte par la caserne 1 .", response.fireStation());
		assertEquals(1, response.persons().size());
		assertEquals("John", response.persons().get(0).firstName());
		assertEquals("Doe", response.persons().get(0).lastName());
		assertTrue(response.persons().get(0).medication().contains("medication"));
		assertTrue(response.persons().get(0).allergy().contains("allergy"));
	}

	@Test
	void personInfoTest() {
		// Given
		Person person = new Person("John", "Doe", "address1", "City", "Zip", "phone1", "email");
		MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1980", Arrays.asList("medication"),
				Arrays.asList("allergy"));
		when(personRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(person);
		when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(medicalRecord);
		when(personRepository.findByLastName("Doe")).thenReturn(
				Arrays.asList(person, new Person("Jane", "Doe", "address1", "City", "Zip", "phone2", "email")));
		// When
		PersonInfoResponsDTO result = urlService.personInfo("John", "Doe");
		// Then
		assertNotNull(result);
		assertEquals("John", result.personFound().firstName());
		assertEquals("Doe", result.personFound().lastName());
		assertEquals(1, result.similarName().size());
		assertTrue(result.similarName().contains("Jane Doe"));
	}

	@Test
	void floodStationsTest() {
		// Given
		when(fireStationRepository.findByStation("1")).thenReturn(Arrays.asList("address1", "address2"));
		when(personRepository.findByAddress("address1"))
				.thenReturn(Arrays.asList(new Person("John", "Doe", "address1", "City", "Zip", "phone1", "email")));
		when(personRepository.findByAddress("address2"))
				.thenReturn(Arrays.asList(new Person("Jane", "Doe", "address2", "City", "Zip", "phone2", "email")));
		when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(new MedicalRecord("John",
				"Doe", "01/01/1980", Arrays.asList("medication1"), Arrays.asList("allergy1")));
		when(medicalRecordRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(new MedicalRecord("Jane",
				"Doe", "02/02/1985", Arrays.asList("medication2"), Arrays.asList("allergy2")));
		// When
		Map<String, List<FloodStationPersonDTO>> response = urlService.floodStations(new String[] { "1" });
		// Then
		assertNotNull(response);
		assertEquals(2, response.size());
		assertTrue(response.containsKey("address1"));
		assertTrue(response.containsKey("address2"));
		assertEquals("John", response.get("address1").get(0).firstName());
		assertEquals("Jane", response.get("address2").get(0).firstName());
	}

	@Test
	void fireStationURLTest() {
		// Given
		when(fireStationRepository.findByStation("1")).thenReturn(Arrays.asList("address1"));
		when(personRepository.findByAddress("address1"))
				.thenReturn(Arrays.asList(new Person("John", "Doe", "address1", "City", "Zip", "phone1", "email"),
						new Person("Jane", "Doe", "address1", "City", "Zip", "phone2", "email")));
		when(medicalRecordRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(
				new MedicalRecord("John", "Doe", "01/01/1980", null, null),
				new MedicalRecord("Jane", "Doe", "01/01/2010", null, null));
		// When
		Map<String, List<CommonDTO>> response = urlService.fireStationURL("1");
		// Then
		assertNotNull(response);
		assertFalse(response.isEmpty());
		assertTrue(response.keySet().iterator().next().contains("Adults: 1, Enfants: 1"));
	}

}
