package com.danieleautizi.service.personaldata.controller;

import com.danieleautizi.service.personaldata.manager.EducationManager;
import com.danieleautizi.service.personaldata.model.presentation.Education;

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
@RequestMapping("/educations")
public class EducationController {

    @Autowired
    private EducationManager educationManager;

    @GetMapping(value = "/{educationId}")
    public Education getEducation(@PathVariable("educationId") final String educationId) {

        LOG.debug("Get education by id {} ", educationId);
        return educationManager.getEducationById(educationId);
    }

    @GetMapping("/")
    public List<Education> getEducations() {

        LOG.debug("Get all educations. ");
        return educationManager.getEducations();
    }

    @GetMapping("/active")
    public List<Education> getActiveEducations() {

        LOG.debug("Get all active educations. ");
        return educationManager.getActiveEducations();
    }

    @PostMapping(value = "/")
    public Education create(@RequestBody final Education education) {

        LOG.debug("Create Education {} ", education);
        return educationManager.create(education);
    }

    @PostMapping(value = "/bulk")
    public List<Education> create(@RequestBody final List<Education> educations) {

        LOG.debug("Create Educations: {} ", educations);
        return educationManager.create(educations);
    }

    @PutMapping(value = "/")
    public Education update(@RequestBody final Education education) {

        LOG.debug("Update Education: {} ", education);
        return educationManager.update(education);
    }

    @DeleteMapping(value = "/{educationId}")
    public void delete(@PathVariable("educationId") final String educationId) {

        LOG.debug("Delete Education by id {} ", educationId);
        educationManager.delete(educationId);
    }

}
