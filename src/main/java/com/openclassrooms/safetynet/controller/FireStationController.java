package com.openclassrooms.safetynet.controller;

import java.util.Collection;

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

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.service.FireStationService;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

	private static final Logger logger = LogManager.getLogger("FireStationController");

	@Autowired
	private FireStationService fireStationService;

	@PostMapping
	public FireStation createFireStation(@RequestBody FireStation fireStation) {
		logger.info("Request Post on firestation begins");
		return fireStationService.createFireStation(fireStation);
	}

	@GetMapping("/all")
	public Collection<FireStation> getAllFireStations() {
		logger.info("Request GetAll on firestation begins");
		return fireStationService.getAllFireStations();
	}

	@PutMapping
	public FireStation updateFireStation(@RequestBody FireStation fireStation) {
		logger.info("Request Put on firestation begins");
		return fireStationService.updateFireStation(fireStation);
	}

	@DeleteMapping
	public void deleteFireStation(@RequestParam String address) {
		logger.info("Request Delete on firestation begins");
		fireStationService.deleteFireStation(address);
	}

}
