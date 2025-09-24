package ru.smallstash.rcoiback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smallstash.rcoiback.entities.Media;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {
    Optional<Media> findByUrl(String url);
}
