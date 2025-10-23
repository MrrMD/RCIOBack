package ru.smallstash.rcoiback.services;

public interface MediaStorageService {
    String upload(byte[] content, String filename);
    void delete(String filename);
}

