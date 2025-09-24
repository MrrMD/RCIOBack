package ru.smallstash.rcoiback.exceptions;

public class NewsNotFoundException extends RuntimeException {
    public NewsNotFoundException(Long id) {
        super("Новость с id=" + id + " не найдена");
    }
}
