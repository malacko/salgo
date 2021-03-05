package com.salgo.baji.person.service.person.impl;

import com.salgo.baji.person.dao.DBFileDao;
import com.salgo.baji.person.dao.PersonDao;
import com.salgo.baji.person.dao.PersonDaoConverter;
import com.salgo.baji.person.exception.PersonException;
import com.salgo.baji.person.pojo.Person;
import com.salgo.baji.person.pojo.PersonConverter;
import com.salgo.baji.person.service.dbfile.DBFileStorageService;
import com.salgo.baji.person.service.person.PersonRepositoryService;
import com.salgo.baji.person.service.person.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequestScope
@EnableScheduling
public class PersonServiceImpl implements PersonService {
  private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);

  @Autowired private PersonRepositoryService personRepositoryService;
  @Autowired private DBFileStorageService dbFileStorageService;

  @Override
  public boolean checkPersonByEmail(String email) {
    return personRepositoryService.existsByEmail(email);
  }

  @Override
  public Long register(Person person) {
    PersonDao personDao = new PersonConverter(person).getDao();
    PersonDao savedDao = personRepositoryService.save(personDao);
    logger.debug(
        "{} created person {} {} with id {} ",
        super.toString(),
        savedDao.getFirstName(),
        savedDao.getLastName(),
        savedDao.getId());
    return savedDao.getId();
  }

  @Override
  @Transactional
  public List<Person> getAllPeople(Integer page, Integer size) {

    int pageRequest = page == null ? 0 : page;
    int sizeRequest = size == null ? 10 : size;
    Pageable sortedByTerritoryAscDateAsc =
        PageRequest.of(pageRequest, sizeRequest, Sort.by("lastName").and(Sort.by("firstName")));
    Page<PersonDao> personDaoPage = personRepositoryService.findAll(sortedByTerritoryAscDateAsc);
    List<PersonDao> daos = personDaoPage.getContent();
    return daos.stream().map(d -> new PersonDaoConverter(d).getPojo()).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public Person getPerson(Long id) {
    PersonDao personDao =
        personRepositoryService
            .findByIdNoFiles(id)
            .orElseThrow(() -> new PersonException("Person not found with id: " + id));

    Set<DBFileDao> dbFileDaoList = dbFileStorageService.getFiles(personDao.getId());
    personDao.setDbFiles(dbFileDaoList);
    return new PersonDaoConverter(personDao).getPojo();
  }

  @Override
  @Transactional
  public Long delete(Long id) {
    PersonDao dao =
        personRepositoryService
            .findById(id)
            .orElseThrow(() -> new PersonException("Person cannot be deleted with id: " + id));
    personRepositoryService.delete(dao);
    return id;
  }

  @Override
  @Transactional
  public Person update(Long id, Person person) {
    if (!personRepositoryService.existsById(id)) {
      throw new PersonException("Person cannot be updated with id: " + id);
    }
    PersonDao personDao = new PersonConverter(person).getDao();
    personDao.setId(id);
    PersonDao savedDao = personRepositoryService.save(personDao);
    logger.debug(
        "person {} {} updated with id {} ",
        savedDao.getFirstName(),
        savedDao.getLastName(),
        savedDao.getId());
    return person;
  }

  @Scheduled(cron = "${profile.cron}")
  @Transactional
  public void cleanup() {
    personRepositoryService.deleteAllByUploadDateBefore(LocalDate.now().minusDays(1));
  }
}
