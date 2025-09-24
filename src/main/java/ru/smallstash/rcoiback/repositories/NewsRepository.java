package ru.smallstash.rcoiback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smallstash.rcoiback.entities.News;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Long> {

    List<News> findByCategory(String category);

}
