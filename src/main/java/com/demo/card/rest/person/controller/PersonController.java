package com.demo.card.rest.person.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.card.rest.person.resource.PersonResource;
import com.demo.card.rest.person.service.PersonService;


@RestController
public class PersonController {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
	
	 @Autowired
	 private PersonService personService;
	 
	 @PostMapping("/insert")
	 public @ResponseBody PersonResource insertUser(@RequestBody PersonResource personResource) {
		 LOGGER.info("Inserting user data: {}", personResource);
		 return personService.insertUser(personResource);
	 }
	 
	 @GetMapping("/persons")
	 public List<PersonResource> getUsers() {
		 LOGGER.info("Getting persons from database");
		 return personService.getAllUsers();
	 }
	 
	 @GetMapping("/person/{oib}")
	 public PersonResource getUser(@PathVariable String oib) {
		 LOGGER.info("Getting user data for OIB: {}", oib);
		 return personService.findByOib(oib);
	 }
	 
	 @DeleteMapping(value = "/delete/{oib}")
	 public @ResponseBody void delete(@PathVariable String oib) {
		 LOGGER.info("Deleting person with OIB: {}", oib);
		 personService.deleteByOib(oib);
	 }
	 
}
