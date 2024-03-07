package com.openclassrooms.safetynet.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.config.Data;
import com.openclassrooms.safetynet.model.Person;

@Repository
public class PersonRepository {

	private Map<String, Person> persons = new HashMap<>();

	public PersonRepository(Data data) {
		initializePersons(data.getPersons());
	}

	private void initializePersons(List<Person> personList) {
		if (personList != null) {
			for (Person person : personList) {
				String key = person.getLastName() + "," + person.getFirstName();
				persons.put(key, person);
			}
		}
	}

	// Autres méthodes du repository
}