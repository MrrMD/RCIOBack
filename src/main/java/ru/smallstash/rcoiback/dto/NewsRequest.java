package ru.smallstash.rcoiback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record NewsRequest(
        @NotBlank(message = "Заголовок обязателен") String title,
        @NotBlank(message = "Текст обязателен") String content,
        @NotBlank(message = "Категория обязательна") String category,
        @Size(max = 100, message = "Можно прикрепить не более 100 медиафайлов")
        List<Long> mediaIds,
        boolean favorite
) {
    public NewsRequest {
        if (mediaIds == null) {
            mediaIds = List.of();
        }
    }
}

