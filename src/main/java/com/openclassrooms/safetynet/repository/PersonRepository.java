package com.openclassrooms.safetynet.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Person;

@Repository
public class PersonRepository {

	private static Map<String, Person> persons = new HashMap<>();

	public static void initializePersons(List<Person> personList) {
		if (personList != null) {
			for (Person person : personList) {
				String key = person.getLastName() + "," + person.getFirstName();
				persons.put(key, person);
			}
		}
	}

	public Person save(Person person) {
		String key = generateKey(person.getFirstName(), person.getLastName());
		persons.put(key, person);
		return person;
	}

	public Person findByFirstNameAndLastName(String firstName, String lastName) {
		return persons.get(generateKey(firstName, lastName));
	}

	public List<Person> findAll() {
		return new ArrayList<>(persons.values());
	}

	public Person update(String firstName, String lastName, Person person) {
		String key = generateKey(firstName, lastName);
		if (persons.containsKey(key)) {
			persons.put(key, person);
			return person;
		}
		return null;
	}

	public void delete(String firstName, String lastName) {
		persons.remove(generateKey(firstName, lastName));
	}

	private static String generateKey(String firstName, String lastName) {
		return lastName + "," + firstName;
	}

	public List<Person> findByCity(String city) {
		List<Person> listPerson = new ArrayList<>();
		for (Map.Entry<String, Person> entry : persons.entrySet()) {
			if (entry.getValue().getCity().equals(city)) {
				listPerson.add(entry.getValue());
			}
		}
		return listPerson;
	}

	public List<Person> findByAddress(String address) {
		List<Person> listPerson = new ArrayList<>();
		for (Map.Entry<String, Person> entry : persons.entrySet()) {
			if (entry.getValue().getAddress().equals(address)) {
				listPerson.add(entry.getValue());
			}
		}
		return listPerson;
	}

	public List<Person> findByLastName(String lastName) {
		List<Person> listPerson = new ArrayList<>();
		for (Map.Entry<String, Person> entry : persons.entrySet()) {
			if (entry.getValue().getLastName().equals(lastName)) {
				listPerson.add(entry.getValue());
			}
		}
		return listPerson;
	}
}