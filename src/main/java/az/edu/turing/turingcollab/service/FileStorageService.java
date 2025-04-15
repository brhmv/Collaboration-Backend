package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.exception.IllegalArgumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${spring.application.upload.dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file, boolean isImage) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is null or empty");
        }

        final long MAX_SIZE = 5 * 1024 * 1024;

        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("File size exceeds limit of 5MB");
        }

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new IllegalArgumentException("Filename is missing");
        }
        String extension = getFileExtension(originalFileName);
        if (isImage && !isSupportedExtension(extension)) {
            throw new IllegalArgumentException("Unsupported file type: " + extension);
        }

        String uniqueFileName = UUID.randomUUID() + "." + extension;

        Path filePath = uploadPath.resolve(uniqueFileName).normalize();
        file.transferTo(filePath.toFile());

        return uniqueFileName;
    }

    public void deleteFile(String fileName) {
        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
        File file = filePath.toFile();

        if (file.exists()) {
            file.delete();
        }
    }

    public String updateFile(String fileName, MultipartFile file, boolean isImage) throws IOException {
        deleteFile(fileName);
        return saveFile(file, isImage);
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    private boolean isSupportedExtension(String extension) {
        return List.of("jpg", "jpeg", "png").contains(extension.toLowerCase());
    }
}

