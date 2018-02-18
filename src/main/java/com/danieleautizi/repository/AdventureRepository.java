package com.danieleautizi.repository;

import com.danieleautizi.model.entity.Adventure;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdventureRepository extends MongoRepository<Adventure, ObjectId> {

    public List<Adventure> findAdventuresByIdIn(final List<ObjectId> adventureIds);

    public List<Adventure> findAdventuresByActive(final boolean active);

}