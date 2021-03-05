package com.salgo.baji.person.service.dbfile.impl;

import com.salgo.baji.person.dao.DBFileDao;
import com.salgo.baji.person.service.dbfile.DBFileRepository;
import com.salgo.baji.person.service.dbfile.DBFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DBFileStorageServiceImpl implements DBFileStorageService {
  @Autowired private DBFileRepository dbFileRepository;

  @Override
  public Set<DBFileDao> getFiles(Long personId) {
    return dbFileRepository.findAllByPersonId(personId);
  }


}
