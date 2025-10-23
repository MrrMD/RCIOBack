package ru.smallstash.rcoiback.services;

import org.springframework.stereotype.Service;

@Service
public class IDriveStorageService implements MediaStorageService {

    @Override
    public String upload(byte[] content, String filename) {
        return "";
    }

    @Override
    public void delete(String filename) {
    }
}

