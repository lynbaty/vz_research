package org.example.es_module.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.es_module.documents.Author;
import org.example.es_module.utils.TimeUtil;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "article")
public class ArticleEntity {
    private Timestamp createTimestamp = TimeUtil.now();
    private Timestamp updateTimestamp = TimeUtil.now();
    private Timestamp deleteTimestamp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json", name = "authors")
    private List<Author> authors;
}
