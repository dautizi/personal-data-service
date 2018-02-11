package com.danieleautizi.repository;

import com.danieleautizi.model.entity.Adventure;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdventureRepository extends MongoRepository<Adventure, String> {

    public Adventure findOneByTitle(String title);

}
