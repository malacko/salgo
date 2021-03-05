package com.salgo.baji.person.pojo;

import com.salgo.baji.person.dao.DBFileDao;
import com.salgo.baji.person.dao.PersonDao;
import com.salgo.baji.person.util.MultiPartFile;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonConverter {

  private final PersonDao dao;

  public PersonConverter(Person person) {
    dao = new PersonDao();
    dao.setEmail(person.getEmail());
    dao.setLastName(person.getLastName());
    dao.setFirstName(person.getFirstName());
    dao.setBirthDate(person.getBirthDate());
    dao.setUploadDate(LocalDate.now());
    dao.setSex(person.getSex());

    MultiPartFile word = new MultiPartFile(person.getWordPath());
    DBFileDao wordDao = new DBFileDao(word.getMultipartFile());
    wordDao.setPersonDao(dao);

    MultiPartFile jpg = new MultiPartFile(person.getPicturePath());
    DBFileDao jpgDao = new DBFileDao(jpg.getMultipartFile());
    jpgDao.setPersonDao(dao);

    MultiPartFile mp3 = new MultiPartFile(person.getMp3Path());
    DBFileDao mp3Dao = new DBFileDao(mp3.getMultipartFile());
    mp3Dao.setPersonDao(dao);

    Set<DBFileDao> dbFileDaos = Stream.of(wordDao, jpgDao, mp3Dao).collect(Collectors.toSet());
    dao.setDbFiles(dbFileDaos);
  }

  public PersonDao getDao() {
    return dao;
  }
}
