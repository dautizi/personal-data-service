package com.danieleautizi.service.personaldata.repository;

import com.danieleautizi.service.personaldata.model.entity.Blog;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BlogRepository extends MongoRepository<Blog, ObjectId> {

    public List<Blog> findBlogsByIdIn(final List<ObjectId> blogIds);

    public List<Blog> findBlogsByActive(final boolean active);

}