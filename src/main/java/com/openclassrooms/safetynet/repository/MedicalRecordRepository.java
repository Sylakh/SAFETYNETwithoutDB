package com.openclassrooms.safetynet.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.config.Data;
import com.openclassrooms.safetynet.model.MedicalRecord;

@Repository
public class MedicalRecordRepository {

	private Map<String, MedicalRecord> medicalRecords = new HashMap<>();

	public MedicalRecordRepository(Data data) {
		initializeMedicalRecords(data.getMedicalrecords());
	}

	private void initializeMedicalRecords(List<MedicalRecord> medicalRecordList) {
		if (medicalRecordList != null) {
			for (MedicalRecord medicalRecord : medicalRecordList) {
				String key = medicalRecord.getLastName() + "," + medicalRecord.getFirstName();
				medicalRecords.put(key, medicalRecord);
			}
		}
	}

	// Autres méthodes du repository
}
