package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@Autowired
	private MedicalRecordService medicalRecordService;

	@PostMapping
	public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		return medicalRecordService.createMedicalRecord(medicalRecord);
	}

	@GetMapping("/{firstName}/{lastName}")
	public MedicalRecord getMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {
		return medicalRecordService.getMedicalRecord(firstName, lastName);
	}

	@GetMapping
	public List<MedicalRecord> getAllMedicalRecords() {
		return medicalRecordService.getAllMedicalRecords();
	}

	@PutMapping
	public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		return medicalRecordService.updateMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName(),
				medicalRecord);
	}

	@DeleteMapping
	public void deleteMedicalRecord(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {
		medicalRecordService.deleteMedicalRecord(firstName, lastName);
	}
}
