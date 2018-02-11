package com.danieleautizi.repository;

import com.danieleautizi.model.entity.AdventureMedia;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdventureMediaRepository extends MongoRepository<AdventureMedia, String> {

}
