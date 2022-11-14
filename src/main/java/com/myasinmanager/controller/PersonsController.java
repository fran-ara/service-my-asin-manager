package com.myasinmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myasinmanager.exception.ProductInfoNotFoundException;
import com.myasinmanager.model.Person;
import com.myasinmanager.model.ProductEntity;
import com.myasinmanager.model.ProductInfo;
import com.myasinmanager.service.PersonService;
import com.myasinmanager.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/persons")
@Slf4j
public class PersonsController {

	@Autowired
	private PersonService personsService;

	@GetMapping
	public ResponseEntity<Iterable<Person>> getAll(Pageable pageable) {
		log.debug("Getting all products with pagination [page = {}, size= {}]", pageable.getPageNumber(),
				pageable.getPageSize());
		return ResponseEntity.ok(personsService.findAll(pageable));
	}
	
	@PostMapping
	public ResponseEntity<Person> create(@RequestBody Person personRequest) {
		log.debug("Creating person with request {}", personRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(personsService.create(personRequest));
	}
}
