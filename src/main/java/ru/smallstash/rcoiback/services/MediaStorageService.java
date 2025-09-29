package ru.smallstash.rcoiback.services;

public interface MediaStorageService {
    String upload(byte[] content, String filename);
    byte[] download(String filename);
    void delete(String filename);
}

