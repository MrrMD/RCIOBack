package ru.smallstash.rcoiback.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record NewsRequest(
        @NotBlank(message = "Заголовок обязателен") String title,
        @NotBlank(message = "Текст обязателен") String content,
        @NotBlank(message = "Категория обязательна") String category,
        List<Long> mediaIds
) {
    public NewsRequest {
        if (mediaIds == null) {
            mediaIds = List.of(); // пустой список вместо null
        }
    }
}

