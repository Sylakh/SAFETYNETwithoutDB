package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@Autowired
	private PersonService personService;

	@PostMapping
	public Person createPerson(@RequestBody Person person) {
		return personService.createPerson(person);
	}

	@GetMapping("/{firstName}/{lastName}")
	public Person getPerson(@PathVariable String firstName, @PathVariable String lastName) {
		return personService.getPerson(firstName, lastName);
	}

	@GetMapping
	public List<Person> getAllPersons() {
		return personService.getAllPersons();
	}

	@PutMapping
	public Person updatePerson(@RequestBody Person person) {
		return personService.updatePerson(person);
	}

	@DeleteMapping
	public void deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
		personService.deletePerson(firstName, lastName);
	}
}
