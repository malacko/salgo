package com.salgo.baji.person.dao;

import com.salgo.baji.person.enums.FileType;
import com.salgo.baji.person.pojo.Person;

import java.util.Set;

public class PersonDaoConverter {

  private final Person pojo;

  public PersonDaoConverter(PersonDao personDao) {
    pojo = new Person();
    pojo.setEmail(personDao.getEmail());
    pojo.setLastName(personDao.getLastName());
    pojo.setFirstName(personDao.getFirstName());
    pojo.setBirthDate(personDao.getBirthDate());
    pojo.setSex(personDao.getSex());

    Set<DBFileDao> dbFiles = personDao.getDbFiles();
    DBFileDao word =
        dbFiles.stream()
            .filter(dao -> FileType.WORD.toString().equals(dao.getFileType()))
            .findFirst()
            .orElse(null);
    pojo.setWordPath(word != null ? word.getFileName() : null);
    DBFileDao mp3 =
        dbFiles.stream()
            .filter(dao -> FileType.MP3.toString().equals(dao.getFileType()))
            .findFirst()
            .orElse(null);
    pojo.setMp3Path(mp3 != null ? mp3.getFileName() : null);
    DBFileDao jpg =
        dbFiles.stream()
            .filter(dao -> FileType.JPG.toString().equals(dao.getFileType()))
            .findFirst()
            .orElse(null);
    pojo.setPicturePath(jpg != null ? jpg.getFileName() : null);
  }

  public Person getPojo() {
    return pojo;
  }
}
