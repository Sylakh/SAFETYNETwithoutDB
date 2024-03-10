package com.openclassrooms.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

class MedicalRecordServiceTest {

	@Mock
	private MedicalRecordRepository medicalRecordRepository;

	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private MedicalRecordService medicalRecordService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createMedicalRecordTest() {
		// Given
		MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1990", null, null);
		when(personRepository.findByFirstNameAndLastName("John", "Doe"))
				.thenReturn(new com.openclassrooms.safetynet.model.Person());
		when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);
		// Given
		MedicalRecord created = medicalRecordService.createMedicalRecord(medicalRecord);
		// Then
		assertNotNull(created);
		verify(medicalRecordRepository).save(medicalRecord);
	}

	@Test
	void getAllMedicalRecordsTest() {
		// Given & When
		medicalRecordService.getAllMedicalRecords();
		// Then
		verify(medicalRecordRepository).findAll();
	}

	@Test
	void updateMedicalRecordTest() {
		// Given
		MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1991", null, null);
		when(medicalRecordRepository.update("John", "Doe", medicalRecord)).thenReturn(medicalRecord);
		// When
		MedicalRecord updated = medicalRecordService.updateMedicalRecord("John", "Doe", medicalRecord);
		// Then
		assertEquals("01/01/1991", updated.getBirthdate());
	}

	@Test
	void deleteMedicalRecordTest() {
		// Given
		doNothing().when(medicalRecordRepository).delete("John", "Doe");
		// When
		medicalRecordService.deleteMedicalRecord("John", "Doe");
		// Then
		verify(medicalRecordRepository).delete("John", "Doe");
	}

	@Test
	void deleteMedicalRecordForDeletedPersonTest() {
		// Given
		doNothing().when(medicalRecordRepository).delete("John", "Doe");
		// When
		medicalRecordService.deleteMedicalRecordForDeletedPerson("John", "Doe");
		// Then
		verify(medicalRecordRepository).delete("John", "Doe");
	}
}
