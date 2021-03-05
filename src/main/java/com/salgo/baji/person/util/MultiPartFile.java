package com.salgo.baji.person.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MultiPartFile {
  private String filePath;

  public MultiPartFile(String filePath) {
    this.filePath = filePath;
  }

  public MultipartFile getMultipartFile() {
    Path path = Paths.get(filePath);
    File file = path.toFile();
    FileItem fileItem = null;
    try {
      fileItem =
          new DiskFileItemFactory()
              .createItem("file", Files.probeContentType(file.toPath()), false, file.getName());
    } catch (IOException e) {
      e.printStackTrace();
    }

    try (InputStream in = new FileInputStream(file);
        OutputStream out = fileItem.getOutputStream()) {
      in.transferTo(out);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid file: " + e, e);
    }

    CommonsMultipartFile multipartFile = new CommonsMultipartFile(fileItem);
    return multipartFile;
  }
}
