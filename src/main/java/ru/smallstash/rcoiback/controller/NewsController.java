package ru.smallstash.rcoiback.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.smallstash.rcoiback.dto.NewsRequest;
import ru.smallstash.rcoiback.dto.NewsResponse;
import ru.smallstash.rcoiback.dto.ViewListRequest;
import ru.smallstash.rcoiback.dto.ViewRequest;
import ru.smallstash.rcoiback.services.NewsService;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping()
    public ResponseEntity<NewsResponse> createNews(@Valid @RequestBody NewsRequest news){
        NewsResponse created = newsService.create(news);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping()
    public ResponseEntity<List<NewsResponse>> getAll() {
        List<NewsResponse> news = newsService.getAll();
        if (news.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(news);
    }

    @GetMapping("/paged")
    public Page<NewsResponse> getAllPaged(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return newsService.getAllPaged(pageable);
    }

    @GetMapping("/{id}")
    public NewsResponse getById(@PathVariable Long id) {
        return newsService.getById(id);
    }


    @GetMapping("/category/{category}")
    public List<NewsResponse> getByCategory(@PathVariable String category) {
        return newsService.getByCategory(category);
    }

    @GetMapping("/favorites")
    public List<NewsResponse> getFavorites() {
        return newsService.getFavorites();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        return newsService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/like")
    public NewsResponse removeLike(@PathVariable Long id) {
        return newsService.removeLike(id);
    }

    @PutMapping("/{id}/like")
    public NewsResponse addLike(@PathVariable Long id) {
        return newsService.addLike(id);
    }

    @PutMapping("/{id}/favorite")
    public NewsResponse addFavorite(@PathVariable Long id) {
        return newsService.setFavorite(id, true);
    }

    @DeleteMapping("/{id}/favorite")
    public NewsResponse removeFavorite(@PathVariable Long id) {
        return newsService.setFavorite(id, false);
    }

    @PostMapping("/views")
    public ResponseEntity<Void> addViews(@Valid @RequestBody ViewListRequest request) {
        newsService.addViews(request.views());
        return ResponseEntity.ok().build();
    }
}