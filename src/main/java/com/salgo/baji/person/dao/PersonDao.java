package com.salgo.baji.person.dao;

import com.salgo.baji.person.enums.Sex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "persons")
public class PersonDao {

  @Id @GeneratedValue private Long id;

  @NotNull private String firstName;

  @NotNull private String lastName;

  @NotNull private LocalDate birthDate;

  @NotNull private LocalDate uploadDate;

  @Email private String email;

  @Enumerated private Sex sex;

  @OneToMany(mappedBy = "personDao", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<DBFileDao> dbFiles;

  public PersonDao(
      Long id, String firstName, String lastName, LocalDate birthDate, String email, Sex sex) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.email = email;
    this.sex = sex;
  }
}
