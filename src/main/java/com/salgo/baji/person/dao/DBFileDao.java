package com.salgo.baji.person.dao;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "db_files")
public class DBFileDao {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String fileId;

  private String fileName;

  private String fileType;

  @Lob private byte[] data;

  @ManyToOne(fetch = FetchType.LAZY)
  private PersonDao personDao;

  public DBFileDao(MultipartFile multipartFile) {
    this.fileName = multipartFile.getOriginalFilename();
    this.fileType = multipartFile.getContentType();
    try {
      this.data = multipartFile.getBytes();
    } catch (IOException ex) {
      ex.getMessage();
    }
  }
}
