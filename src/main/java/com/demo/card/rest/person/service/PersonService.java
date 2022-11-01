package com.demo.card.rest.person.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.card.db.entity.Person;
import com.demo.card.db.repository.PersonRepository;
import com.demo.card.rest.person.resource.PersonResource;
import com.demo.card.rest.person.resource.PersonResourceAssembler;
import com.opencsv.CSVWriter;

@Service
public class PersonService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PersonResourceAssembler personResourceAssembler;
	
	public PersonResource insertUser(PersonResource resource) {
		Person person = new Person();
		
		if (resource.getId() == null) {
			person = personResourceAssembler.toEntity(resource);
		} else {
			person = personRepository.findById(resource.getId()).get();
			
			person = personResourceAssembler.updateEntity(person, resource);
		}
		
		try {
			LOGGER.debug("Saving person: {}", person);
			personRepository.save(person);
			LOGGER.debug("Saving user successfully done: {}", person);
			
		}catch (Exception e) {
			LOGGER.error("Error creating person:" + e.getMessage(), e);
			return new PersonResource();
		}
		
		PersonResource result = personResourceAssembler.toModel(person);
		
		return result;
		
	}
	
	public List<PersonResource> getAllUsers() {
		try {
			LOGGER.debug("Getting all persons from database...");
			List<PersonResource> resources = new ArrayList<>();
			for (Person entity : personRepository.findAll()) {
				PersonResource resource = new PersonResource();
				resource = personResourceAssembler.toModel(entity);
				resources.add(resource);
			}
			return resources;
		} catch (Exception e) {
			LOGGER.info("Error getting all persons from database: ", e.getMessage(), e);
			return new ArrayList<>();
		}
	}
	
	public PersonResource findByOib(String oib) {
		Person person = null;
		
		try {
			LOGGER.debug("Getting user from DB with OIB: {}", oib);
			person = personRepository.findByOib(oib);
			exportToCSV(person);
		} catch (Exception e) {
			LOGGER.info("Error getting person from database: ", e.getMessage(), e);
			return new PersonResource();
		}
		
		if (person == null) {
			LOGGER.info("Person with OIB: {} does not exist.", oib);
			return new PersonResource();
		}
		
		PersonResource personResource = personResourceAssembler.toModel(person);
		
		return personResource;
	}
	
	public void deleteByOib(String oib) {
		try {
			LOGGER.debug("Deleting person with OIB: {}", oib);
			personRepository.deleteByOib(oib);
			LOGGER.debug("Person deleted with OIB: {}", oib);
			
			File file = new File("./" + oib + ".csv");
			File rename = new File("./" + oib + "(INACTIVE).csv");
			file.renameTo(rename);
		} catch (Exception e) {
			LOGGER.error("Error deleting person with OIB: {}", oib + ", message: " + e.getMessage(), e);
		}
	}
	
	public void exportToCSV(Person person) {
		String filePath = "./" + person.getOib() + ".csv";
		File file = new File(filePath);
		LOGGER.info("Exporting to csv...");
		if (file.exists() == false ) {
			try {
				FileWriter outputFile = new FileWriter(file);
				CSVWriter writer = new CSVWriter(outputFile, ';',
	                    CSVWriter.NO_QUOTE_CHARACTER,
	                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                    CSVWriter.DEFAULT_LINE_END);
				String[] data = {person.getFirstName(), person.getLastName(), person.getOib(), person.getStatus()};
				
				LOGGER.info("Exporting to csv done.");
				writer.writeNext(data);
				
				writer.close();
				
			} catch (Exception e){
				e.printStackTrace();
			}
		} else {
			LOGGER.info("File already exists. Exporting canceled.");
		}
	}
}
