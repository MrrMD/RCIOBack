package ru.smallstash.rcoiback.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.smallstash.rcoiback.dto.MediaResponse;
import ru.smallstash.rcoiback.entities.Media;
import ru.smallstash.rcoiback.repositories.MediaRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final MediaStorageService mediaStorageService;

    public MediaService(MediaRepository mediaRepository, MediaStorageService mediaStorageService) {
        this.mediaRepository = mediaRepository;
        this.mediaStorageService = mediaStorageService;
    }

    public MediaResponse handleUpload(MultipartFile file) throws IOException {
        String url = mediaStorageService.upload(file.getBytes(), file.getOriginalFilename());

        Media media = new Media();
        media.setUrl(url);
        media.setType(file.getContentType());

        Media saved = mediaRepository.save(media);

        return new MediaResponse(saved.getId(), saved.getUrl(), saved.getType());
    }

    public List<MediaResponse> handleUploadMultiple(MultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> {
                    try {
                        return handleUpload(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    public boolean deleteMedia(Long id) {
        return mediaRepository.findById(id).map(media -> {
            mediaStorageService.delete(media.getUrl());
            mediaRepository.delete(media);
            return true;
        }).orElse(false);
    }
}
