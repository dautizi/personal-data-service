package com.danieleautizi.service.personaldata.repository;

import com.danieleautizi.service.personaldata.model.entity.CurriculumVitae;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CurriculumVitaeRepository extends MongoRepository<CurriculumVitae, ObjectId> {

    List<CurriculumVitae> findCurriculumVitaeByActiveOrderByPrgDesc(final boolean active);

}
