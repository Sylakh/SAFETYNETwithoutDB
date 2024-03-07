package com.openclassrooms.safetynet.config;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;

@Component
public class ReaderFromFile implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		try {
			File file = new File("data.json");

			// Convertir le fichier JSON en un objet Java en utilisant Jackson ObjectMapper
			ObjectMapper objectMapper = new ObjectMapper();
			Data data = objectMapper.readValue(file, Data.class);

			// Traiter les données comme vous le souhaitez
			for (Person person : data.getPersons()) {
				System.out.println("Personne : " + person.getFirstName() + " " + person.getLastName());
			}
			for (FireStation fireStation : data.getFirestations()) {
				System.out.println("Station d'incendie à l'adresse : " + fireStation.getAddress());
			}
			for (MedicalRecord medicalRecord : data.getMedicalrecords()) {
				System.out.println(
						"Dossier médical pour : " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
			}

		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
