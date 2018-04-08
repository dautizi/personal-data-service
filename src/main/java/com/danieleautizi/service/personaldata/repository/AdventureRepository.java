package com.danieleautizi.service.personaldata.repository;

import com.danieleautizi.service.personaldata.model.entity.Adventure;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdventureRepository extends MongoRepository<Adventure, ObjectId> {

    List<Adventure> findAdventuresByIdInOrderByPrgAsc(final List<ObjectId> adventureIds);

    List<Adventure> findAdventuresByActiveOrderByPrgAsc(final boolean active);

    Adventure findFirstByActiveIsTrueAndPrg(final int prg);
}
