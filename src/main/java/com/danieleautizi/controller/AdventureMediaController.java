package com.danieleautizi.controller;

import com.danieleautizi.manager.AdventureMediaManager;
import com.danieleautizi.model.presentation.AdventureMedia;

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
@RequestMapping("/adventures/media")
public class AdventureMediaController {

    @Autowired
    private AdventureMediaManager adventureMediaManager;

    @GetMapping(value = "/{adventureMediaId}")
    public AdventureMedia getAdventureMedia(@PathVariable("adventureMediaId") final String adventureMediaId) {

        LOG.debug("Get adventureMedia by id {} ", adventureMediaId);
        return adventureMediaManager.getAdventureMediaById(adventureMediaId);
    }

    @GetMapping("/")
    public List<AdventureMedia> getAdventureMedias() {

        LOG.debug("Get all adventureMedia. ");
        return adventureMediaManager.getAdventureMedia();
    }

    @GetMapping("/active")
    public List<AdventureMedia> getActiveAdventureMedias() {

        LOG.debug("Get all active adventureMedia. ");
        return adventureMediaManager.getActiveAdventureMedia();
    }

    @PostMapping(value = "/")
    public AdventureMedia create(@RequestBody final AdventureMedia adventureMedia) {

        LOG.debug("Create AdventureMedia {} ", adventureMedia);
        return adventureMediaManager.create(adventureMedia);
    }

    @PostMapping(value = "/bulk")
    public List<AdventureMedia> create(@RequestBody final List<AdventureMedia> adventureMedias) {

        LOG.debug("Create AdventureMedias: {} ", adventureMedias);
        return adventureMediaManager.create(adventureMedias);
    }

    @PutMapping(value = "/")
    public void update(@RequestBody final AdventureMedia adventureMedia) {

        LOG.debug("Update AdventureMedia: {} ", adventureMedia);
        adventureMediaManager.update(adventureMedia);
    }

    @DeleteMapping(value = "/{adventureMediaId}")
    public void delete(@PathVariable("adventureMediaId") final String adventureMediaId) {

        LOG.debug("Delete AdventureMedia by id {} ", adventureMediaId);
        adventureMediaManager.delete(adventureMediaId);
    }

}
