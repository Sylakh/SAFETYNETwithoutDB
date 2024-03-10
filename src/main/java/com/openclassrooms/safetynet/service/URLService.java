package com.openclassrooms.safetynet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.DTO.ChildAlertResponsDTO;
import com.openclassrooms.safetynet.DTO.CommonDTO;
import com.openclassrooms.safetynet.DTO.FireResponsDTO;
import com.openclassrooms.safetynet.DTO.FloodStationPersonDTO;
import com.openclassrooms.safetynet.DTO.PersonInfoResponsDTO;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class URLService {

	private static final Logger logger = LogManager.getLogger("URLService");

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private FireStationRepository fireStationRepository;

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public List<String> communityEmail(String city) {
		List<Person> listPerson = personRepository.findByCity(city);
		List<String> communityEmail = new ArrayList<>();
		for (Person person : listPerson) {
			communityEmail.add(person.getEmail());
		}
		logger.info("Request communityEmail done");
		return communityEmail;
	}

	public List<String> phoneAlert(String station) {
		List<String> listAddress = fireStationRepository.findByStation(station);
		List<String> listPhone = new ArrayList<>();
		for (String address : listAddress) {
			for (Person person : personRepository.findByAddress(address)) {
				listPhone.add(person.getPhone());
			}
		}
		logger.info("Request phoneAlert done");
		return listPhone;
	}

	public ChildAlertResponsDTO childAlert(String address) {
		List<CommonDTO> listChild = new ArrayList<>();
		List<CommonDTO> listAdult = new ArrayList<>();
		List<Person> listPerson = personRepository.findByAddress(address);
		for (Person person : listPerson) {
			MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(),
					person.getLastName());
			if (medicalRecord.calculateAge(medicalRecord.getBirthdate()) > 18) {
				listAdult.add(new CommonDTO(medicalRecord.getFirstName(), medicalRecord.getLastName(), null, null, null,
						null, null, null));
			} else {
				listChild.add(new CommonDTO(medicalRecord.getFirstName(), medicalRecord.getLastName(), null, null, null,
						medicalRecord.calculateAge(medicalRecord.getBirthdate()), null, null));
			}
		}
		ChildAlertResponsDTO childAlertRespons = new ChildAlertResponsDTO(listChild, listAdult);
		if (listChild.size() == 0) {
			childAlertRespons = null;
			logger.info("No child at the address " + address);
		}
		logger.info("Request childAlert done");
		return childAlertRespons;
	}

	public FireResponsDTO fire(String address) {
		List<CommonDTO> listPersonDTO = new ArrayList<>();
		List<Person> listPerson = personRepository.findByAddress(address);
		if (listPerson != null) {
			for (Person person : listPerson) {
				MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(),
						person.getLastName());
				listPersonDTO.add(new CommonDTO(person.getFirstName(), person.getLastName(), person.getPhone(), null,
						null, medicalRecord.calculateAge(medicalRecord.getBirthdate()),
						medicalRecord.listToString(medicalRecord.getMedications()),
						medicalRecord.listToString(medicalRecord.getAllergies())));
			}
			logger.info("Request fire done");
			return new FireResponsDTO("l'adresse " + address + " est couverte par la caserne "
					+ fireStationRepository.findByAddress(address).getStation() + " .", listPersonDTO);
		} else {
			logger.info("This address is not registered");
			return null;
		}
	}

	public PersonInfoResponsDTO personInfo(String firstName, String lastName) {
		Person person = personRepository.findByFirstNameAndLastName(firstName, lastName);
		MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
		CommonDTO personInfoDTO = new CommonDTO(person.getFirstName(), person.getLastName(), null, person.getAddress(),
				null, medicalRecord.calculateAge(medicalRecord.getBirthdate()),
				medicalRecord.listToString(medicalRecord.getMedications()),
				medicalRecord.listToString(medicalRecord.getAllergies()));
		List<Person> listSimilarName = personRepository.findByLastName(lastName);
		List<String> similarName = new ArrayList<>();
		for (Person personSimilar : listSimilarName) {
			if (!personSimilar.getFirstName().equals(firstName)) {
				similarName.add(personSimilar.getFirstName() + " " + personSimilar.getLastName());
			}
		}
		logger.info("Request personInfo done");
		return new PersonInfoResponsDTO(personInfoDTO, similarName);
	}

	public Map<String, List<FloodStationPersonDTO>> floodStations(String[] stations) {
		List<String> listAddress = new ArrayList<>();
		Map<String, List<FloodStationPersonDTO>> floodStationsRespons = new HashMap<>();
		for (String station : stations) {

			for (String fireStation : fireStationRepository.findByStation(station)) {
				listAddress.add(fireStation);
			}
		}

		for (String address : listAddress) {
			List<FloodStationPersonDTO> listAllPersonConcerned = new ArrayList<>();
			for (Person person : personRepository.findByAddress(address)) {
				MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(),
						person.getLastName());
				listAllPersonConcerned.add(new FloodStationPersonDTO(person.getFirstName(), person.getLastName(),
						person.getPhone(), medicalRecord.calculateAge(medicalRecord.getBirthdate()),
						medicalRecord.getMedications(), medicalRecord.getAllergies()));
			}
			floodStationsRespons.put(address, listAllPersonConcerned);

		}
		logger.info("Request flood/stations done");
		return floodStationsRespons;
	}

	public Map<String, List<CommonDTO>> fireStationURL(String station) {
		List<String> listAddress = new ArrayList<>();
		List<CommonDTO> listPersonCovered = new ArrayList<>();
		int countAdult = 0;
		int countChildren = 0;
		for (String fireStation : fireStationRepository.findByStation(station)) {
			listAddress.add(fireStation);
		}
		for (String address : listAddress) {
			for (Person person : personRepository.findByAddress(address)) {
				MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(),
						person.getLastName());
				listPersonCovered.add(new CommonDTO(person.getFirstName(), person.getLastName(), person.getPhone(),
						person.getAddress(), null, medicalRecord.calculateAge(medicalRecord.getBirthdate()), null,
						null));
				if (medicalRecord.calculateAge(medicalRecord.getBirthdate()) > 18) {
					countAdult += 1;
				} else {
					countChildren += 1;
				}
			}
		}
		Map<String, List<CommonDTO>> map = new HashMap<>();
		String key = "Adults: " + countAdult + ", Enfants: " + countChildren;
		map.put(key, listPersonCovered);
		logger.info("Request firestation done");
		return map;
	}

}
