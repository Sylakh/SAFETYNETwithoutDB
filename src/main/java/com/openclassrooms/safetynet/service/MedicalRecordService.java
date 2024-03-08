package com.openclassrooms.safetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Autowired
	private PersonRepository personRepository;

	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
		if (personExists(medicalRecord.getFirstName(), medicalRecord.getLastName())) {
			return medicalRecordRepository.save(medicalRecord);
		}
		return null; // Or handle this case as per your application's requirements
	}

	public MedicalRecord getMedicalRecord(String firstName, String lastName) {
		return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalRecordRepository.findAll();
	}

	public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord) {
		return medicalRecordRepository.update(firstName, lastName, medicalRecord);
	}

	public void deleteMedicalRecord(String firstName, String lastName) {
		medicalRecordRepository.delete(firstName, lastName);
	}

	private boolean personExists(String firstName, String lastName) {
		return personRepository.findByFirstNameAndLastName(firstName, lastName) != null;
	}

	public void deleteMedicalRecordForDeletedPerson(String firstName, String lastName) {
		medicalRecordRepository.delete(firstName, lastName);
	}
}
