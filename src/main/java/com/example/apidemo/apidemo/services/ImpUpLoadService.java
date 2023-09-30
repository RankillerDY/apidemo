package com.example.apidemo.apidemo.services;

import com.example.apidemo.apidemo.database.Database;
import org.apache.commons.io.FileSystem;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImpUpLoadService implements UpLoadFileService {
    private final Path storageFolder = Paths.get("uploads");
    //path tham chiếu đến thư mục up load ảnh trog cùng thư mục dự án

    private static final Logger logger = LoggerFactory.getLogger(ImpUpLoadService.class);

    public ImpUpLoadService() {
        try {
            Files.createDirectories(storageFolder);
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize storage ", e);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename()).trim().toLowerCase();
        logger.info("This is the file extension " + fileExtension);
        boolean flag = Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"}).contains(fileExtension);
        logger.info("The flag value is " + flag);
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension);
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Cannot found file");
            }
            logger.info(String.valueOf(file.getOriginalFilename()).toString());

            if (!isImageFile(file)) {
                throw new RuntimeException("The file is not an image");
            }
            //file size must be <= 5mb
            float fileSizeInMegaByte = file.getSize() / 1_000_000;
            if (fileSizeInMegaByte > 5f) {
                throw new RuntimeException("File must be <= 5mb");
            }
            //File must be rename
            //Khi file lên server bắt buộc phải đổi tên
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String FileNameGenerator = UUID.randomUUID().toString().replace("-", "");
            String newFileName = FileNameGenerator + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(
                    Paths.get(FileNameGenerator)
            ).normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return newFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file ", e);
        }

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public byte[] readFileContent(String filename) {
        return new byte[0];
    }

    @Override
    public void deleteAllFiles() {

    }
}
