package com.openclassrooms.safetynet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.safetynet.model.MedicalRecord;

class MedicalRecordRepositoryTest {

	private MedicalRecordRepository medicalRecordRepository;

	@BeforeEach
	void setUp() {
		medicalRecordRepository = new MedicalRecordRepository();
		MedicalRecordRepository.initializeMedicalRecords(Arrays.asList(
				new MedicalRecord("John", "Doe", "01/01/1990", Arrays.asList("medication1"), Arrays.asList("allergy1")),
				new MedicalRecord("Jane", "Doe", "02/02/1990", Arrays.asList("medication2"),
						Arrays.asList("allergy2"))));
	}

	@Test
	void saveAndFindByFirstNameAndLastNameTest() {
		// Given
		MedicalRecord newRecord = new MedicalRecord("John", "Snow", "03/03/1990", Arrays.asList("medication3"),
				Arrays.asList("allergy3"));
		// When
		medicalRecordRepository.save(newRecord);
		MedicalRecord foundRecord = medicalRecordRepository.findByFirstNameAndLastName("John", "Snow");
		// Then
		assertEquals(newRecord, foundRecord);
	}

	@Test
	void findAllTest() {
		// Given & When
		List<MedicalRecord> records = medicalRecordRepository.findAll();
		// Then
		assertTrue(records.size() >= 2);
	}

	@Test
	void updateTest() {
		// Given
		MedicalRecord updatedRecord = new MedicalRecord("John", "Doe", "04/04/1990", Arrays.asList("medication4"),
				Arrays.asList("allergy4"));
		// When
		MedicalRecord result = medicalRecordRepository.update("John", "Doe", updatedRecord);
		// Then
		assertNotNull(result);
		assertEquals("04/04/1990", result.getBirthdate());
	}

	@Test
	void deleteTest() {
		// Given & When
		medicalRecordRepository.delete("Jane", "Doe");
		// Then
		assertNull(medicalRecordRepository.findByFirstNameAndLastName("Jane", "Doe"));
	}
}
