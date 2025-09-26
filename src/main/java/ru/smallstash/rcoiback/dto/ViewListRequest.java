package ru.smallstash.rcoiback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ViewListRequest(

        @NotEmpty(message = "Список просмотров не должен быть пустым")
        List<@Valid ViewRequest> views

) {}
