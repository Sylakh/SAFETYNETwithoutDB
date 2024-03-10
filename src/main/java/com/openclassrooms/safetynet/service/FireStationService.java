package com.openclassrooms.safetynet.service;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.repository.FireStationRepository;

@Service
public class FireStationService {

	private static final Logger logger = LogManager.getLogger("FireStationService");

	@Autowired
	private FireStationRepository fireStationRepository;

	public FireStation createFireStation(FireStation fireStation) {
		logger.info("Request Post on firestation done");
		return fireStationRepository.save(fireStation);
	}

	public Collection<FireStation> getAllFireStations() {
		logger.info("Request Get all on firestation done");
		return fireStationRepository.findAll();
	}

	public FireStation updateFireStation(FireStation fireStation) {
		logger.info("Request Put on firestation done");
		return fireStationRepository.update(fireStation.getAddress(), fireStation);
	}

	public void deleteFireStation(String address) {
		logger.info("Request Delete on firestation done");
		fireStationRepository.delete(address);
	}

}
