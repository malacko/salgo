package com.salgo.baji.person.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

public class Util {
  private static Util _instance = new Util();

  private Util() {}

  public static Util getInstance() {
    return _instance;
  }

  public File getFileFromResource(String fileName) throws URISyntaxException {

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      return new File(resource.toURI());
    }
  }

  public String getFilePathFromResource(String fileName) throws URISyntaxException {

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      File file = new File(resource.toURI());
      return file.toPath().toString();
    }
  }

  public String getFileType(String fileName) {
    String ext = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
    switch (ext.toLowerCase(Locale.ROOT)) {
      case "doc":
        return "WORD";
      case "jpg":
        return "JPG";
      case "mp3":
        return "MP3";
      default:
        return null;
    }
  }
}
