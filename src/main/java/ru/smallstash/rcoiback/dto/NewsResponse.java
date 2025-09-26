package ru.smallstash.rcoiback.dto;

import java.time.LocalDateTime;
import java.util.List;

public record NewsResponse(

        Long id,
        String title,
        String content,
        String category,
        List<MediaResponse> media,
        LocalDateTime createdAt,
        int likes,
        boolean favorite,
        int views
) {}
