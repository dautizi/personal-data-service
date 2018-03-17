package com.danieleautizi.service.personaldata.controller;

import com.danieleautizi.service.personaldata.manager.AdventureManager;
import com.danieleautizi.service.personaldata.model.presentation.Adventure;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/adventures")
public class AdventureController {

    @Autowired
    private AdventureManager adventureManager;

    @GetMapping(value = "/{adventureId}")
    public Adventure getAdventure(@PathVariable("adventureId") final String adventureId) {

        LOG.debug("Get adventure by id {} ", adventureId);
        return adventureManager.getAdventureById(adventureId);
    }

    @GetMapping("/")
    public List<Adventure> getAdventures() {

        LOG.debug("Get all adventures. ");
        return adventureManager.getAdventures();
    }

    @GetMapping("/active")
    public List<Adventure> getActiveAdventures() {

        LOG.debug("Get all active adventures. ");
        return adventureManager.getActiveAdventures();
    }

    @PostMapping(value = "/")
    public Adventure create(@RequestBody final Adventure adventure) {

        LOG.debug("Create Adventure {} ", adventure);
        return adventureManager.create(adventure);
    }

    @PostMapping(value = "/bulk")
    public List<Adventure> create(@RequestBody final List<Adventure> adventures) {

        LOG.debug("Create Adventures: {} ", adventures);
        return adventureManager.create(adventures);
    }

    @PutMapping(value = "/")
    public void update(@RequestBody final Adventure adventure) {

        LOG.debug("Update Adventure: {} ", adventure);
        adventureManager.update(adventure);
    }

    @DeleteMapping(value = "/{adventureId}")
    public void delete(@PathVariable("adventureId") final String adventureId) {

        LOG.debug("Delete Adventure by id {} ", adventureId);
        adventureManager.delete(adventureId);
    }

}
