package com.danieleautizi.service.personaldata.repository;

import com.danieleautizi.service.personaldata.model.entity.AdventureMedia;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdventureMediaRepository extends MongoRepository<AdventureMedia, ObjectId> {

    List<AdventureMedia> findAdventureMediaByIdIn(final List<ObjectId> adventureMediaIds);

    List<AdventureMedia> findAdventureMediaByActive(final boolean active);

    AdventureMedia findAdventureMediaByMediaPath(final String adventureMediaPath);
}
