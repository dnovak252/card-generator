package com.demo.card.db.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.card.db.entity.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
	Person findByOib(String oib);
	Person deleteByOib(String oib);
}
