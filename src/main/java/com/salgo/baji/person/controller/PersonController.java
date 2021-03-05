package com.salgo.baji.person.controller;

import com.salgo.baji.person.exception.PersonException;
import com.salgo.baji.person.pojo.Person;
import com.salgo.baji.person.service.person.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController()
@RequestScope
@RequestMapping("/api")
public class PersonController {
  private static final Logger logger = LogManager.getLogger(PersonController.class);
  @Autowired PersonService personService;
  @Autowired ResourceLoader resourceLoader;

  @PostMapping(
      value = "/registerPerson",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody ResponseEntity registerPerson(@Valid @RequestBody Person person) {
    if (personService.checkPersonByEmail(person.getEmail())) {
      throw new PersonException("This e-mail registered before: " + person.getEmail());
    }
    logger.debug(
        "{} request regeisterPerson for {} {} ",
        super.toString(),
        person.getFirstName(),
        person.getLastName());
    Long id = personService.register(person);

    return ResponseEntity.status(HttpStatus.OK).body(id);
  }

  @GetMapping(value = "/people", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody ResponseEntity<List<Person>> getPeople(
      @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    List<Person> people = personService.getAllPeople(page, size);
    return ResponseEntity.status(HttpStatus.OK).body(people);
  }

  @GetMapping(value = "/person/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody ResponseEntity<Person> getPersonById(@PathVariable Long id) {
    Person person = personService.getPerson(id);
    return ResponseEntity.status(HttpStatus.OK).body(person);
  }

  @DeleteMapping(value = "/deletePerson/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody ResponseEntity deletePerson(@PathVariable Long id) {
    id = personService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).body(id);
  }

  @PutMapping(
      value = "/updatePerson/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody ResponseEntity updatePerson(
      @PathVariable Long id, @Valid @RequestBody Person person) {
    person = personService.update(id, person);
    return ResponseEntity.status(HttpStatus.OK).body(person);
  }

  @GetMapping(value = "cleanup")
  public @ResponseBody ResponseEntity cleanUP() {
    personService.cleanup();
    return ResponseEntity.status(HttpStatus.OK).body("OK");
  }

  @GetMapping(value = "resourcepath")
  public @ResponseBody ResponseEntity getpath() throws IOException {
    File folder = new ClassPathResource("static").getFile();

    return ResponseEntity.status(HttpStatus.OK).body(folder.toPath());
  }
}
