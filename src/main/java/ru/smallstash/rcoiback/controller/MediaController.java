package ru.smallstash.rcoiback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.smallstash.rcoiback.dto.MediaRequest;
import ru.smallstash.rcoiback.dto.MediaResponse;
import ru.smallstash.rcoiback.entities.Media;
import ru.smallstash.rcoiback.repositories.MediaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaRepository mediaRepository;

    public MediaController(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @PostMapping
    public ResponseEntity<MediaResponse> upload(@RequestBody MediaRequest request) {
        Media media = new Media();
        media.setUrl(request.url());
        media.setType(request.type());
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
