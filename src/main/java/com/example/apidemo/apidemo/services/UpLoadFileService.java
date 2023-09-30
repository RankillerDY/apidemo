package com.example.apidemo.apidemo.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface UpLoadFileService {
    public String storeFile(MultipartFile file);

    public Stream<Path> loadAll();

    public byte[] readFileContent(String filename);

    public void deleteAllFiles();
}
