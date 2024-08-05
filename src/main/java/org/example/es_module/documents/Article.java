package org.example.es_module.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.List;

import static org.example.es_module.utils.Constant.ARTICLE_INDEX;
import static org.springframework.data.elasticsearch.annotations.FieldType.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = ARTICLE_INDEX)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Article {
    @Id
    private Long id;

    private String title;
    @Field(type =FieldType.Date, format = DateFormat.date_hour_minute_second)
    private Timestamp updateTimestamp;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Author> authors;
}