package com.danieleautizi.controller;

import com.danieleautizi.model.entity.Adventure;
import com.danieleautizi.repository.AdventureRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adventures")
public class AdventureController {

    @Autowired
    AdventureRepository adventureRepository;

    // create
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Adventure adventure) {
		adventureRepository.save(adventure);
	}

    // read
    @RequestMapping(value = "/{id}")
    public Adventure read(@PathVariable ObjectId id) {

        // adventureRepository.findOne(id);
        return null;
    }

    // update
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Adventure adventure) {
        adventureRepository.save(adventure);
        adventureRepository.findOneByTitle("name01");
    }

    // delete
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
		adventureRepository.delete(id);
	}

}
