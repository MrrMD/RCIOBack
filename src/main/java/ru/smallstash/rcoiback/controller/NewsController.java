package ru.smallstash.rcoiback.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.smallstash.rcoiback.dto.NewsRequest;
import ru.smallstash.rcoiback.dto.NewsResponse;
import ru.smallstash.rcoiback.services.NewsService;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping()
    public List<NewsResponse> getAll() {
        return newsService.getAll();
    }

    @GetMapping("/paged")
    public Page<NewsResponse> getAllPaged(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return newsService.getAllPaged(pageable);
    }

    @GetMapping("/{id}")
    public NewsResponse getById(@PathVariable Long id) {
        return newsService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<NewsResponse> createNews(@RequestBody NewsRequest news){
        NewsResponse created = newsService.create(news);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean deleted = newsService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/like")
    public NewsResponse removeLike(@PathVariable Long id) {
        return newsService.removeLike(id);
    }

    @PutMapping("/{id}/like")
    public NewsResponse addLike(@PathVariable Long id) {
        return newsService.addLike(id);
    }

}