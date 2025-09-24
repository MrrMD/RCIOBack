package ru.smallstash.rcoiback.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.smallstash.rcoiback.dto.MediaResponse;
import ru.smallstash.rcoiback.dto.NewsRequest;
import ru.smallstash.rcoiback.dto.NewsResponse;
import ru.smallstash.rcoiback.entities.Media;
import ru.smallstash.rcoiback.entities.News;
import ru.smallstash.rcoiback.exceptions.NewsNotFoundException;
import ru.smallstash.rcoiback.repositories.MediaRepository;
import ru.smallstash.rcoiback.repositories.NewsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final MediaRepository mediaRepository;

    public NewsService(NewsRepository newsRepository, MediaRepository mediaRepository) {
        this.newsRepository = newsRepository;
        this.mediaRepository = mediaRepository;
    }

    public List<NewsResponse> getAll(){
        return newsRepository.findAll()
                .stream()
                .map(this::mapToNewsResponseDTO)
                .toList();
    }

    public Page<NewsResponse> getAllPaged(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .map(this::mapToNewsResponseDTO);
    }

    public boolean deleteById(Long id){
        if(newsRepository.existsById(id)){
            newsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public NewsResponse findById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));
        return mapToNewsResponseDTO(news);
    }

    public NewsResponse create(NewsRequest request) {
        News news = new News();
        news.setTitle(request.title());
        news.setContent(request.content());
        news.setCategory(request.category());
        news.setCreatedAt(LocalDateTime.now());
        news.setLikes(0);
        List<Media> mediaList = mediaRepository.findAllById(request.mediaIds());
        news.setMedia(mediaList);

        News saved = newsRepository.save(news);

        return mapToNewsResponseDTO(saved);
    }

    public NewsResponse addLike(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));

        news.setLikes(news.getLikes() + 1);

        News saved = newsRepository.save(news);
        return mapToNewsResponseDTO(saved);
    }

    public NewsResponse removeLike(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));

        if (news.getLikes() > 0) {
            news.setLikes(news.getLikes() - 1);
        }

        News saved = newsRepository.save(news);
        return mapToNewsResponseDTO(saved);
    }

    private NewsResponse mapToNewsResponseDTO(News entity){
        return new NewsResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCategory(),
                entity.getMedia().stream()
                        .map(m -> new MediaResponse(m.getId(), m.getUrl(), m.getType()))
                        .toList(),
                entity.getCreatedAt(),
                entity.getLikes()
        );
    }
}
