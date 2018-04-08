package com.danieleautizi.service.personaldata.repository;

import com.danieleautizi.service.personaldata.model.entity.Image;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image, ObjectId> {

    List<Image> findImagesByIdInOrderByPrgAsc(final List<ObjectId> imageIds);

    List<Image> findImagesByActiveOrderByPrgAsc(final boolean active);

}
