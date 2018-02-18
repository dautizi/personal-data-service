package com.danieleautizi.repository;

import com.danieleautizi.model.entity.Article;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, ObjectId> {

    public List<Article> findArticlesByIdIn(final List<ObjectId> articleIds);

    public List<Article> findArticlesByActive(final boolean active);

}
