package com.danieleautizi.repository;

import com.danieleautizi.model.entity.Skill;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillRepository extends MongoRepository<Skill, ObjectId> {

    public List<Skill> findSkillsByIdIn(final List<ObjectId> skillIds);

    public List<Skill> findSkillsByActive(final boolean active);

}
