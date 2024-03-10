package com.openclassrooms.safetynet.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

@Service
public class MedicalRecordService {

	private static final Logger logger = LogManager.getLogger("MedicalRecordService");

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Autowired
	private PersonRepository personRepository;

	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
		if (personExists(medicalRecord.getFirstName(), medicalRecord.getLastName())) {
			logger.info("Request Post on medicalrecord done");
			return medicalRecordRepository.save(medicalRecord);
		}
		return null; // Or handle this case as per your application's requirements
	}

	/**
	 * public MedicalRecord getMedicalRecord(String firstName, String lastName) {
	 * return medicalRecordRepository.findByFirstNameAndLastName(firstName,
	 * lastName); }
	 */
	public List<MedicalRecord> getAllMedicalRecords() {
		logger.info("Request Get all on medicalrecord done");
		return medicalRecordRepository.findAll();
	}

	public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord) {
		logger.info("Request Put on medicalrecord done");
		return medicalRecordRepository.update(firstName, lastName, medicalRecord);
	}

	public void deleteMedicalRecord(String firstName, String lastName) {
		logger.info("Request Delete on medicalrecord done");
		medicalRecordRepository.delete(firstName, lastName);
	}

	private boolean personExists(String firstName, String lastName) {
		return personRepository.findByFirstNameAndLastName(firstName, lastName) != null;
	}

	public void deleteMedicalRecordForDeletedPerson(String firstName, String lastName) {
		medicalRecordRepository.delete(firstName, lastName);
	}

}
