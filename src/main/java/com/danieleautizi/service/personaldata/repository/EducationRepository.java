package com.danieleautizi.service.personaldata.repository;

import com.danieleautizi.service.personaldata.model.entity.Education;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EducationRepository extends MongoRepository<Education, ObjectId> {

    List<Education> findEducationsByIdInOrderByPrgAsc(final List<ObjectId> educationIds);

    List<Education> findEducationsByActiveOrderByPrgAsc(final boolean active);
}
