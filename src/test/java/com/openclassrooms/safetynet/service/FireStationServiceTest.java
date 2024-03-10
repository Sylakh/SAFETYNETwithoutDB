package com.openclassrooms.safetynet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.repository.FireStationRepository;

public class FireStationServiceTest {

	@Mock
	private FireStationRepository fireStationRepository;

	@InjectMocks
	private FireStationService fireStationService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void createFireStationTest() {
		// Given
		FireStation fireStation = new FireStation("address", "1");
		when(fireStationRepository.save(fireStation)).thenReturn(fireStation);
		// When
		FireStation created = fireStationService.createFireStation(fireStation);
		// Then
		assertEquals(fireStation, created);
		verify(fireStationRepository).save(fireStation);
	}

	@Test
	public void getAllFireStationsTest() {
		// Given
		FireStation fireStation1 = new FireStation("address1", "1");
		FireStation fireStation2 = new FireStation("address2", "2");
		when(fireStationRepository.findAll()).thenReturn(Arrays.asList(fireStation1, fireStation2));
		// When
		Collection<FireStation> fireStations = fireStationService.getAllFireStations();
		// Then
		assertEquals(2, fireStations.size());
		assertTrue(fireStations.containsAll(Arrays.asList(fireStation1, fireStation2)));
	}

	@Test
	public void updateFireStationTest() {
		// Given
		FireStation fireStation = new FireStation("address", "1");
		when(fireStationRepository.update("address", fireStation)).thenReturn(fireStation);
		// When
		FireStation updated = fireStationService.updateFireStation(fireStation);
		// Then
		assertEquals(fireStation, updated);
		verify(fireStationRepository).update("address", fireStation);
	}

	@Test
	public void deleteFireStationTest() {
		// Given
		doNothing().when(fireStationRepository).delete("address");
		// When
		fireStationService.deleteFireStation("address");
		// Then
		verify(fireStationRepository).delete("address");
	}
}
