package com.openclassrooms.safetynet.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.repository.FireStationRepository;

@Service
public class FireStationService {

	@Autowired
	private FireStationRepository fireStationRepository;

	public FireStation createFireStation(FireStation fireStation) {
		return fireStationRepository.save(fireStation);
	}

	public FireStation getFireStation(String address) {
		return fireStationRepository.findByAddress(address);
	}

	public Collection<FireStation> getAllFireStations() {
		return fireStationRepository.findAll();
	}

	public FireStation updateFireStation(FireStation fireStation) {
		return fireStationRepository.update(fireStation.getAddress(), fireStation);
	}

	public void deleteFireStation(String address) {
		fireStationRepository.delete(address);
	}

}
