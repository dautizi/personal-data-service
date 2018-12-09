package com.danieleautizi.service.personaldata.controller;

import com.danieleautizi.service.personaldata.manager.CurriculumVitaeManager;
import com.danieleautizi.service.personaldata.model.presentation.CurriculumVitae;

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
@RequestMapping("/curricula-vitae")
public class CurriculumVitaeController {

    @Autowired
    private CurriculumVitaeManager curriculumVitaeManager;

    @GetMapping(value = "/{cvId}")
    public CurriculumVitae getCurriculumVitae(@PathVariable("cvId") final String cvId) {

        LOG.debug("Get curriculum vitae by id {} ", cvId);
        return curriculumVitaeManager.getCurriculumVitaeById(cvId);
    }

    @GetMapping(value = "/last")
    public CurriculumVitae getCurriculumVitae() {

        LOG.debug("Get last curriculum vitae");
        return curriculumVitaeManager.getLastCurriculumVitae();
    }

    @GetMapping("/")
    public List<CurriculumVitae> getCurriculaVitae() {

        LOG.debug("Get all curriculaVitae. ");
        return curriculumVitaeManager.getCurriculaVitae();
    }

    @GetMapping("/active")
    public List<CurriculumVitae> getActiveCurriculaVitae() {

        LOG.debug("Get all active curriculaVitae. ");
        return curriculumVitaeManager.getActiveCurriculaVitae();
    }

    @PostMapping(value = "/")
    public CurriculumVitae create(@RequestBody final CurriculumVitae cv) {

        LOG.debug("Create curriculumVitae {} ", cv);
        return curriculumVitaeManager.create(cv);
    }

    @PutMapping(value = "/")
    public CurriculumVitae update(@RequestBody final CurriculumVitae cv) {

        LOG.debug("Update curriculumVitae: {} ", cv);
        return curriculumVitaeManager.update(cv);
    }

    @DeleteMapping(value = "/{curriculumVitaeId}")
    public void delete(@PathVariable("curriculumVitaeId") final String cvId) {

        LOG.debug("Delete curriculumVitae by id {} ", cvId);
        curriculumVitaeManager.delete(cvId);
    }

}
