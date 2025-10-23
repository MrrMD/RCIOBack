package ru.smallstash.rcoiback.controller;

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

    @PostMapping
    public ResponseEntity<MediaResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
        MediaResponse response = mediaService.handleUpload(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<MediaResponse> getAll() {
        return mediaRepository.findAll().stream()
                .map(m -> new MediaResponse(m.getId(), m.getUrl(), m.getType()))
                .toList();
    }
}
