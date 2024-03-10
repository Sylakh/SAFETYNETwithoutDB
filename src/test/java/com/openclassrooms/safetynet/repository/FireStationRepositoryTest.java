package com.openclassrooms.safetynet.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.safetynet.model.FireStation;

public class FireStationRepositoryTest {

	private FireStationRepository fireStationRepository;

	@BeforeEach
	public void setUp() {
		fireStationRepository = new FireStationRepository();
		FireStationRepository.initializeFireStations(
				Arrays.asList(new FireStation("address1", "1"), new FireStation("address2", "2")));
	}

	@Test
	public void saveTest() {
		// Given
		FireStation newFireStation = new FireStation("address3", "3");
		// When
		FireStation savedFireStation = fireStationRepository.save(newFireStation);
		// Then
		assertEquals(newFireStation, savedFireStation);
		assertEquals(newFireStation, fireStationRepository.findByAddress("address3"));
	}

	@Test
	public void findByAddressTest() {
		// Given & When
		FireStation fireStation = fireStationRepository.findByAddress("address1");
		// Then
		assertNotNull(fireStation);
		assertEquals("1", fireStation.getStation());
	}

	@Test
	public void findAllTest() {
		// Given & When
		List<FireStation> fireStations = fireStationRepository.findAll();
		// Then
		assertThat(1 < fireStations.size());
	}

	@Test
	public void updateTest() {
		// Given
		FireStation updatedFireStation = new FireStation("address1", "10");
		// When
		FireStation result = fireStationRepository.update("address1", updatedFireStation);
		// Then
		assertEquals(updatedFireStation, result);
		assertEquals("10", fireStationRepository.findByAddress("address1").getStation());
	}

	@Test
	public void deleteTest() {
		// Given & When
		fireStationRepository.delete("address1");
		// Then
		assertNull(fireStationRepository.findByAddress("address1"));
	}

	@Test
	public void findByStationTest() {
		// Given & When
		List<String> addresses = fireStationRepository.findByStation("2");
		// Then
		assertThat(0 < addresses.size());
		assertTrue(addresses.contains("address2"));
	}
}
