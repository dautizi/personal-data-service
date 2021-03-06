package com.danieleautizi.service.personaldata.repository;

import com.danieleautizi.service.personaldata.model.entity.Article;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, ObjectId> {

    List<Article> findArticlesByIdInOrderByPrgAsc(final List<ObjectId> articleIds);

    List<Article> findArticlesByActiveOrderByPrgAsc(final boolean active);

}
