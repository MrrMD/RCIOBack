package ru.smallstash.rcoiback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.smallstash.rcoiback.entities.News;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Long> {

    List<News> findByCategory(String category);
    List<News> findByFavoriteTrue();
    @Modifying
    @Query("UPDATE News n SET n.views = n.views + :count WHERE n.id = :id")
    void incrementViews(@Param("id") Long id, @Param("count") int count);
}
