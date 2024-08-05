package org.example.es_module.repositories.jpa;

import org.example.es_module.documents.Article;
import org.example.es_module.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface JPAArticleRepository extends JpaRepository<ArticleEntity, Long> {
    @Query(value = """
    SELECT * FROM article WHERE update_timestamp > :latestUpdate AND update_timestamp < :now
""", nativeQuery = true)
    List<ArticleEntity> getEntitiesForUpdate(Timestamp latestUpdate, Timestamp now);
}
