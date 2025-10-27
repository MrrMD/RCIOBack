package ru.smallstash.rcoiback.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.smallstash.rcoiback.dto.MediaResponse;
import ru.smallstash.rcoiback.repositories.MediaRepository;
import ru.smallstash.rcoiback.services.MediaService;
import ru.smallstash.rcoiback.services.MediaStorageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaRepository mediaRepository;
    private final MediaService mediaService;

    public MediaController(MediaRepository mediaRepository, MediaService mediaService) {
        this.mediaRepository = mediaRepository;
        this.mediaService = mediaService;
    }

    @Operation(summary = "Загрузка одного файла")
    @PostMapping
    public ResponseEntity<MediaResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
        MediaResponse response = mediaService.handleUpload(file);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Загрузка нескольких файлов")
    @PostMapping("/multiple")
    public ResponseEntity<List<MediaResponse>> uploadMultiple(@RequestParam("files") MultipartFile[] files) {
        List<MediaResponse> responses = mediaService.handleUploadMultiple(files);
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    public List<MediaResponse> getAll() {
        return mediaRepository.findAll().stream()
                .map(media -> new MediaResponse(
                        media.getId(),
                        media.getUrl(),
                        media.getType()
                ))
                .toList();
    }
}
