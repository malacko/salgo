package com.salgo.baji.person.service.dbfile;

import com.salgo.baji.person.dao.DBFileDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DBFileRepository extends CrudRepository<DBFileDao, String> {

  @Query("SELECT f FROM DBFileDao f WHERE f.personDao.id = :pid")
  Set<DBFileDao> findAllByPersonId(Long pid);
}
