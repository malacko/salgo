package com.salgo.baji.person.service.dbfile;

import com.salgo.baji.person.dao.DBFileDao;

import java.util.Set;

public interface DBFileStorageService {
  Set<DBFileDao> getFiles(Long personId);
}
