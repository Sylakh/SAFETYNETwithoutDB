package com.openclassrooms.safetynet.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.FireStation;

@Repository
public class FireStationRepository {
	private static Map<String, FireStation> fireStations = new HashMap<>();

	public static void initializeFireStations(List<FireStation> list) {
		if (list != null) {
			for (FireStation fireStation : list) {
				String key = fireStation.getAddress();
				fireStations.put(key, fireStation);
			}
		}
	}

	public FireStation save(FireStation fireStation) {
		fireStations.put(fireStation.getAddress(), fireStation);
		return fireStation;
	}

	public FireStation findByAddress(String address) {
		return fireStations.get(address);
	}

	public List<FireStation> findAll() {
		return new ArrayList<>(fireStations.values());
	}

	public FireStation update(String address, FireStation fireStation) {
		if (fireStations.containsKey(address)) {
			fireStations.put(address, fireStation);
			return fireStation;
		}
		return null;
	}

	public void delete(String address) {
		fireStations.remove(address);
	}

	public List<String> findByStation(String station) {
		List<String> listAddress = new ArrayList<>();
		for (Map.Entry<String, FireStation> entry : fireStations.entrySet()) {
			if (entry.getValue().getStation().equals(station)) {
				listAddress.add(entry.getKey());
			}
		}
		return listAddress;
	}
}