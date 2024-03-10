package com.openclassrooms.safetynet.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

public class MedicalRecord {

	private String firstName;
	private String lastName;
	private String birthdate;
	private List<String> medications;
	private List<String> allergies;

	public MedicalRecord() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

	public MedicalRecord(String firstName, String lastName, String birthdate, List<String> medications,
			List<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
	}

	public Date convertStringToDate(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			// Convertir la chaîne en objet Date
			Date date = dateFormat.parse(dateString);
			return date;
		} catch (ParseException e) {
			// Gérer l'exception si la conversion échoue
			throw new IllegalArgumentException("Date: " + dateString + " should be of the format : MM/dd/yyyy");
		}
	}

	public int calculateAge(String birthDate) {

		Date dateOfBirth = convertStringToDate(birthDate);
		Calendar birthDateCal = Calendar.getInstance();
		birthDateCal.setTime(dateOfBirth);

		Calendar nowCal = Calendar.getInstance();

		int age = nowCal.get(Calendar.YEAR) - birthDateCal.get(Calendar.YEAR);

		// Vérifier si l'anniversaire n'a pas encore eu lieu cette année
		if (nowCal.get(Calendar.DAY_OF_YEAR) < birthDateCal.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}

		return age;

	}

	public String listToString(List<String> list) {

		StringJoiner joiner = new StringJoiner(", ");
		for (String element : list) {
			joiner.add(element);
		}
		return joiner.toString();
	}

}
