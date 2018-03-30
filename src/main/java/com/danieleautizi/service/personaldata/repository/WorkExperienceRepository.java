package com.danieleautizi.service.personaldata.repository;

import com.danieleautizi.service.personaldata.model.entity.WorkExperience;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorkExperienceRepository extends MongoRepository<WorkExperience, ObjectId> {

    List<WorkExperience> findWorkExperiencesByIdIn(final List<ObjectId> workExperienceIds);

    List<WorkExperience> findWorkExperiencesByActive(final boolean active);
}
