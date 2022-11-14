package com.myasinmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myasinmanager.model.Person;
import com.myasinmanager.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonService {

	@Autowired
	private PersonRepository personsRepository;

	public Iterable<Person> findAll(Pageable pageable) {
		Iterable<Person> personsPaginated = personsRepository.findAll();
		log.debug("Response  findAll:{}", personsPaginated);
		return personsPaginated;
	}

	public Person create(Person person) {
		personsRepository.save(person);
		return person;
	}
}
