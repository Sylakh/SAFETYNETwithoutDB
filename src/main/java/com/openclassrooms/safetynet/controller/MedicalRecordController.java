package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;

@RestController
@RequestMapping("/medicalrecord")
public class MedicalRecordController {

	private static final Logger logger = LogManager.getLogger("MedicalRecordController");

	@Autowired
	private MedicalRecordService medicalRecordService;

	@PostMapping
	public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		logger.info("Request Post on medicalrecord begins");
		return medicalRecordService.createMedicalRecord(medicalRecord);
	}

	@GetMapping("/all")
	public List<MedicalRecord> getAllMedicalRecords() {
		logger.info("Request Get all on medicalrecord begins");
		return medicalRecordService.getAllMedicalRecords();
	}

	@PutMapping
	public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		logger.info("Request Put on medicalrecord begins");
		return medicalRecordService.updateMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName(),
				medicalRecord);
	}

	@DeleteMapping
	public void deleteMedicalRecord(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {
		logger.info("Request Delete on medicalrecord begins");
		medicalRecordService.deleteMedicalRecord(firstName, lastName);
	}
}
