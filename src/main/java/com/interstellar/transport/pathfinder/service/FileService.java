package com.interstellar.transport.pathfinder.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileService {

  public Path getLatestFileName(Resource resource) throws IOException {
    Path dir = null;
    try{
      dir = Paths.get(resource.getURI());
    }catch (FileNotFoundException fileNotFoundException){
      log.error("Unable to find excel file {}", fileNotFoundException.getMessage());
      //throw new DataDumpException("Unable to find excel file", fileNotFoundException.getCause());
    }
    return dir;
  }

  public File getFileToRead(Path fileName, Resource resource) throws IOException, URISyntaxException {
    Path dir = Paths.get(resource.getURI());
    return new File(new URI(dir.toUri().toString()));
  }

}
