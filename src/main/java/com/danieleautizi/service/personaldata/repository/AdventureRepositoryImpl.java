package com.danieleautizi.service.personaldata.repository;

import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AdventureRepositoryImpl implements AdventureRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String ADVENTURE_COLLECTION = "adventure";
    private static final String ADVENTURE_TYPE_FIELD = "adventureType";
    private static final String ACTIVE_FIELD = "active";

    public Set<String> findAllAdventureTypes() {

        val query = new Query(Criteria.where(ACTIVE_FIELD)
                                      .is(true));

        @SuppressWarnings("unchecked")
        final List<String> adventureTypes = mongoTemplate.getCollection(ADVENTURE_COLLECTION)
                                                         .distinct(ADVENTURE_TYPE_FIELD, query.getQueryObject());

        return new HashSet<>(adventureTypes);
    }
}
