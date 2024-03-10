package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	private static final Logger logger = LogManager.getLogger("PersonController");

	@Autowired
	private PersonService personService;

	@PostMapping
	public Person createPerson(@RequestBody Person person) {
		logger.info("Request Post on person begins");
		return personService.createPerson(person);
	}

	@GetMapping("/all")
	public List<Person> getAllPersons() {
		logger.info("Request Get all on person begins");
		return personService.getAllPersons();
	}

	@PutMapping
	public Person updatePerson(@RequestBody Person person) {
		logger.info("Request Put on person begins");
		return personService.updatePerson(person);
	}

	@DeleteMapping
	public void deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
		logger.info("Request Delete on person begins");
		personService.deletePerson(firstName, lastName);
	}
}
