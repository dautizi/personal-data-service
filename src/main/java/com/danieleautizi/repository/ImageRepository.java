package com.danieleautizi.repository;

import com.danieleautizi.model.entity.Image;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image, ObjectId> {

    public List<Image> findImagesByIdIn(final List<ObjectId> imageIds);

    public List<Image> findImagesByActive(final boolean active);

}
