package org.example.es_module.controllers;

import org.example.es_module.documents.Article;
import org.example.es_module.entities.ArticleEntity;
import org.example.es_module.services.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("expenses")
public class ExpensesController {

    private final ArticleService articleService;

    public ExpensesController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public ResponseEntity<?> getExpenses(@RequestParam String authorName) {
        return ResponseEntity.ok(articleService.findAll(authorName, PageRequest.of(0, 10)));
    }

    @PostMapping()
    public ResponseEntity<?> getExpenses(@RequestBody Article article) {
        articleService.save(article);
        return ResponseEntity.ok("save article");
    }

    @PostMapping("/saveAll")
    public ResponseEntity<?> saveAll(@RequestBody List<Article> articles) {
        articleService.saveAll(articles);
        return ResponseEntity.ok("saved all articles");
    }

    @GetMapping("/fuzzy")
    public ResponseEntity<List<Article>> fuzzy(@RequestParam String near, @RequestParam String keySearch) throws IOException {
        return ResponseEntity.ok(articleService.fuzzy(near, keySearch, PageRequest.of(0, 10)));
    }

    @GetMapping("/getByTenant")
    public ResponseEntity<Page<ArticleEntity>> getAll(@RequestParam (defaultValue = "0") Integer page,
                                                      @RequestParam (defaultValue = "5") Integer size) throws IOException {
        return ResponseEntity.ok(articleService.getAll( PageRequest.of(page, size)));
    }
}
