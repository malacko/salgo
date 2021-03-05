package com.salgo.baji.person.service.person;

import com.salgo.baji.person.pojo.Person;

import java.util.List;

public interface PersonService {

  boolean checkPersonByEmail(String email);

  Long register(Person person);

  List<Person> getAllPeople(Integer page, Integer size);

  Person getPerson(Long id);

  Long delete(Long id);

  Person update(Long id, Person person);

  void cleanup();
}
