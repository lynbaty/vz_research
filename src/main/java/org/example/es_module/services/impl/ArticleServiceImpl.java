package org.example.es_module.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.example.es_module.documents.Article;
import org.example.es_module.entities.ArticleEntity;
import org.example.es_module.repositories.els.ArticleRepository;
import org.example.es_module.repositories.jpa.JPAArticleRepository;
import org.example.es_module.services.ArticleService;
import org.example.es_module.utils.ESUtil;
import org.example.es_module.utils.TimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.example.es_module.utils.Constant.ARTICLE_INDEX;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ElasticsearchClient elasticsearchClient;
    private final JPAArticleRepository jpaArticleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, ElasticsearchClient elasticsearchClient, JPAArticleRepository jpaArticleRepository) {
        this.articleRepository = articleRepository;
        this.elasticsearchClient = elasticsearchClient;
        this.jpaArticleRepository = jpaArticleRepository;
    }

    @Override
    public void save(Article article) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(article.getTitle());
        entity.setAuthors(article.getAuthors());
        jpaArticleRepository.save(entity);
        syncArticles();
    }

    @Override
    public void saveAll(List<Article> articles) {
        List<ArticleEntity> articleEntities = new ArrayList<>();
        for (Article article : articles) {
            ArticleEntity entity = new ArticleEntity();
            entity.setTitle(article.getTitle());
            entity.setAuthors(article.getAuthors());
            articleEntities.add(entity);
        }
        jpaArticleRepository.saveAll(articleEntities);
        syncArticles();
    }

    @Override
    public Page<Article> findAll(String authorName, Pageable pageable) {

        return articleRepository.findByAuthorsName(authorName, pageable);
    }

    @Override
    public List<Article> fuzzy(String near, String keySearch, Pageable pageable) throws IOException {
        SearchResponse<Article> response = elasticsearchClient.search(b -> b.index(ARTICLE_INDEX)
                                                                           .query(q -> q.match(m -> m.field("title")
                                                                                   .query(near)
                                                                                   .operator(Operator.And)
                                                                                   .fuzziness("AUTO"))), Article.class);

//        SearchResponse<Article> response = elasticsearchClient.search(b -> b.index(ARTICLE_INDEX)
//                                                                            .query(q -> q.bool(bq -> bq.should(List.of(
//                                                                                    new Query.Builder().fuzzy(f -> f.field("title").value(near)).build(),
//                                                                                    new Query.Builder().matchPhrase(m -> m.field("title").query(near)).build()
//                                                                            )))), Article.class);

        return ESUtil.toList(response.hits().hits());
    }

    @Override
    public Page<ArticleEntity> getAll(Pageable pageable) {
        return jpaArticleRepository.findAll(pageable);
    }

    public Timestamp getTopTimestamp(){
        try {
            SearchResponse<Article> response = elasticsearchClient.search(b -> b.index(ARTICLE_INDEX)
                                                                                .size(1)
                                                                                .sort(s -> s.field(f -> f.field("updateTimestamp")
                                                                                                         .order(SortOrder.Desc))
                                                                                ), Article.class);
            return response.hits().hits().stream().findFirst().get().source().getUpdateTimestamp();
        } catch (Exception e) {
            return new Timestamp(System.currentTimeMillis() - 10_000);
        }
    }

    @Async
    public void syncArticles(){
        Timestamp latestUpdate = getTopTimestamp();;
        List<ArticleEntity> entities = jpaArticleRepository.getEntitiesForUpdate(latestUpdate, TimeUtil.now());
        List<Article> articles = new ArrayList<>();
        for (ArticleEntity entity : entities) {
            Article article = new Article();
            article.setTitle(entity.getTitle());
            article.setAuthors(entity.getAuthors());
            article.setUpdateTimestamp(entity.getUpdateTimestamp());
            articles.add(article);
        }
        articleRepository.saveAll(articles);
    }
}
