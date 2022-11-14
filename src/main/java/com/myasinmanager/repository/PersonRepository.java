package com.myasinmanager.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myasinmanager.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByName(String name);
}