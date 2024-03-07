package com.openclassrooms.safetynet.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.config.Data;
import com.openclassrooms.safetynet.model.FireStation;

@Repository
public class FireStationRepository {

	private Map<String, FireStation> fireStations = new HashMap<>();

	public FireStationRepository(Data data) {
		initializeFireStations(data.getFirestations());
	}

	private void initializeFireStations(List<FireStation> list) {
		if (list != null) {
			for (FireStation fireStation : list) {
				String key = fireStation.getAddress();
				fireStations.put(key, fireStation);
			}
		}
	}

	// Autres méthodes du repository
}
