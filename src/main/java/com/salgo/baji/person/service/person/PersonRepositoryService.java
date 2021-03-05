package com.salgo.baji.person.service.person;

import com.salgo.baji.person.dao.PersonDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PersonRepositoryService extends PagingAndSortingRepository<PersonDao, Long> {

  @Query(
      "SELECT new com.salgo.baji.person.dao.PersonDao(p.id, p.firstName, p.lastName, p.birthDate, p.email, p.sex) FROM PersonDao p WHERE p.id = :id")
  Optional<PersonDao> findByIdNoFiles(@Param("id") Long id);

  void deleteAllByUploadDateBefore(@Param("uploadDate") LocalDate uploadDate);

  @Query("SELECT p.id FROM PersonDao p WHERE p.uploadDate < :uploadDate")
  Set<Long> findAllPersonIdsOlderThan(@Param("uploadDate") LocalDate uploadDate);

  boolean existsByEmail(String email);
}
