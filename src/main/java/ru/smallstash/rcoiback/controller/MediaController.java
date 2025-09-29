package ru.smallstash.rcoiback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.smallstash.rcoiback.dto.MediaResponse;
import ru.smallstash.rcoiback.entities.Media;
import ru.smallstash.rcoiback.repositories.MediaRepository;
import ru.smallstash.rcoiback.services.MediaStorageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaRepository mediaRepository;
    private final MediaStorageService mediaStorageService;

    public MediaController(MediaRepository mediaRepository, MediaStorageService mediaStorageService) {
        this.mediaRepository = mediaRepository;
        this.mediaStorageService = mediaStorageService;
    }

    @PostMapping()
    public ResponseEntity<MediaResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String url = mediaStorageService.upload(file.getBytes(), file.getOriginalFilename());

        Media media = new Media();
        media.setUrl(url);
        media.setType(file.getContentType());
        Media saved = mediaRepository.save(media);

        return ResponseEntity.ok(new MediaResponse(saved.getId(), saved.getUrl(), saved.getType()));
    }

    @GetMapping
    public List<MediaResponse> getAll() {
        return mediaRepository.findAll().stream()
                .map(m -> new MediaResponse(m.getId(), m.getUrl(), m.getType()))
                .toList();
    }
}
