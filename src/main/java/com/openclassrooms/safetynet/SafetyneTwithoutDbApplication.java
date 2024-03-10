package com.openclassrooms.safetynet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.openclassrooms.safetynet.config.RawData;
import com.openclassrooms.safetynet.config.ReaderFromFile;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

@SpringBootApplication
public class SafetyneTwithoutDbApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SafetyneTwithoutDbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		RawData rawData = ReaderFromFile.readData();
		FireStationRepository.initializeFireStations(rawData.getFirestations());
		MedicalRecordRepository.initializeMedicalRecords(rawData.getMedicalrecords());
		PersonRepository.initializePersons(rawData.getPersons());
		/**
		 * // Traiter les données comme vous le souhaitez for (Person person :
		 * rawData.getPersons()) { System.out.println("Personne : " +
		 * person.getFirstName() + " " + person.getLastName()); } for (FireStation
		 * fireStation : rawData.getFirestations()) { System.out.println("Station
		 * d'incendie à l'adresse : " + fireStation.getAddress()); } for (MedicalRecord
		 * medicalRecord : rawData.getMedicalrecords()) { System.out.println( "Dossier
		 * médical pour : " + medicalRecord.getFirstName() + " " +
		 * medicalRecord.getLastName()); }
		 */
	}

}
