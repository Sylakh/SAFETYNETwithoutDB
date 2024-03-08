package com.openclassrooms.safetynet.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.config.RawData;
import com.openclassrooms.safetynet.model.MedicalRecord;

@Repository
public class MedicalRecordRepository {

	private static Map<String, MedicalRecord> medicalRecords = new HashMap<>();

	public MedicalRecordRepository(RawData data) {
		initializeMedicalRecords(data.getMedicalrecords());
	}

	public static void initializeMedicalRecords(List<MedicalRecord> medicalRecordList) {
		if (medicalRecordList != null) {
			for (MedicalRecord medicalRecord : medicalRecordList) {
				String key = medicalRecord.getLastName() + "," + medicalRecord.getFirstName();
				medicalRecords.put(key, medicalRecord);
			}
		}
	}

	public MedicalRecord save(MedicalRecord medicalRecord) {
		String key = generateKey(medicalRecord.getFirstName(), medicalRecord.getLastName());
		medicalRecords.put(key, medicalRecord);
		return medicalRecord;
	}

	public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {
		return medicalRecords.get(generateKey(firstName, lastName));
	}

	public List<MedicalRecord> findAll() {
		return new ArrayList<>(medicalRecords.values());
	}

	public MedicalRecord update(String firstName, String lastName, MedicalRecord medicalRecord) {
		String key = generateKey(firstName, lastName);
		if (medicalRecords.containsKey(key)) {
			medicalRecords.put(key, medicalRecord);
			return medicalRecord;
		}
		return null;
	}

	public void delete(String firstName, String lastName) {
		medicalRecords.remove(generateKey(firstName, lastName));
	}

	private static String generateKey(String firstName, String lastName) {
		return lastName + "," + firstName;
	}
}
