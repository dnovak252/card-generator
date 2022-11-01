package com.demo.card.rest.person.resource;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

import com.demo.card.db.entity.Person;
import com.demo.card.rest.person.controller.PersonController;

@Service
public class PersonResourceAssembler extends RepresentationModelAssemblerSupport<Person, PersonResource>{
	
	public PersonResourceAssembler() {
		super(PersonController.class, PersonResource.class);
	}

	public PersonResource toModel (Person personEntity) {
		PersonResource personResource = new PersonResource();
		
		personResource.setId(personEntity.getId());
		personResource.setFirstName(personEntity.getFirstName());
		personResource.setLastName(personEntity.getLastName());
		personResource.setOib(personEntity.getOib());
		personResource.setStatus(personEntity.getStatus());
		
		return personResource;
	}
	
	public Person toEntity(PersonResource personResource) {
		Person personEntity = new Person();
		
		personEntity.setFirstName(personResource.getFirstName());
		personEntity.setLastName(personResource.getLastName());
		personEntity.setOib(personResource.getOib());
		personEntity.setStatus(personResource.getStatus());
		
		return personEntity;
	}
	
	public Person updateEntity(Person personEntity, PersonResource personResource) {
		personEntity.setFirstName(personResource.getFirstName());
		personEntity.setLastName(personResource.getLastName());
		personEntity.setOib(personResource.getOib());
		personEntity.setStatus(personResource.getStatus());
		
		return personEntity;
	}
}
