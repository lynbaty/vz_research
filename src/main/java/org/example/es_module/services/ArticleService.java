package org.example.es_module.services;

import org.example.es_module.documents.Article;
import org.example.es_module.entities.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ArticleService {
    void save(Article article);

    void saveAll(List<Article> articles);

    Page<Article> findAll(String authorName, Pageable pageable);

    List<Article> fuzzy(String near, String keySearch, Pageable pageable) throws IOException;

    Page<ArticleEntity> getAll(Pageable pageable);
}
