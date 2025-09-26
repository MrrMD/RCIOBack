package ru.smallstash.rcoiback.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ViewRequest(
        @NotNull(message = "ID новости обязателен") Long id,
        @Min(value = 1, message = "Количество просмотров должно быть минимум 1") int count
) {}

