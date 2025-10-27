package ru.smallstash.rcoiback.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class DriveStorageService implements MediaStorageService {


    private static final String STORAGE_PATH = "F:/uploads/";

    public DriveStorageService() {
        File dir = new File(STORAGE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public String upload(byte[] content, String filename) {
        try {
            Path filePath = Path.of(STORAGE_PATH, filename);


            if (Files.exists(filePath)) {
                String name = filename;
                String ext = "";
                int dotIndex = filename.lastIndexOf('.');
                if (dotIndex > 0) {
                    name = filename.substring(0, dotIndex);
                    ext = filename.substring(dotIndex);
                }
                int i = 1;
                while (Files.exists(filePath)) {
                    filePath = Path.of(STORAGE_PATH, name + "_" + i + ext);
                    i++;
                }
            }

            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(content);
            }

            String fileName = filePath.getFileName().toString();
            return "http://188.0.167.1:8080/media/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении файла: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String filename) {
        try {
            String cleanFilename = filename.contains("media/")
                    ? filename.substring(filename.lastIndexOf("media/") + 6)
                    : filename;

            Path filePath = Path.of(STORAGE_PATH, cleanFilename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при удалении файла: " + e.getMessage(), e);
        }
    }
}
