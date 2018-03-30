package com.danieleautizi.service.personaldata.repository;

import com.danieleautizi.service.personaldata.model.entity.Skill;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillRepository extends MongoRepository<Skill, ObjectId> {

    List<Skill> findSkillsByIdIn(final List<ObjectId> skillIds);

    List<Skill> findSkillsByActive(final boolean active);

}
