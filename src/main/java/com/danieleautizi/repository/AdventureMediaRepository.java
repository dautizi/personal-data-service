package com.danieleautizi.repository;

import com.danieleautizi.model.entity.AdventureMedia;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdventureMediaRepository extends MongoRepository<AdventureMedia, ObjectId> {

    public List<AdventureMedia> findAdventureMediaByIdIn(final List<ObjectId> adventureMediaIds);

    public List<AdventureMedia> findAdventureMediaByActive(final boolean active);

}
