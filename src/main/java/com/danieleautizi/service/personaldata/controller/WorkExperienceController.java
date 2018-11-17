package com.danieleautizi.service.personaldata.controller;

import com.danieleautizi.service.personaldata.manager.WorkExperienceManager;
import com.danieleautizi.service.personaldata.model.presentation.WorkExperience;

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
@RequestMapping("/work-experiences")
public class WorkExperienceController {

    @Autowired
    private WorkExperienceManager workExperienceManager;

    @GetMapping(value = "/{workExperienceId}")
    public WorkExperience getWorkExperience(@PathVariable("workExperienceId") final String workExperienceId) {

        LOG.debug("Get workExperience by id {} ", workExperienceId);
        return workExperienceManager.getWorkExperienceById(workExperienceId);
    }

    @GetMapping("/")
    public List<WorkExperience> getWorkExperiences() {

        LOG.debug("Get all workExperiences. ");
        return workExperienceManager.getWorkExperiences();
    }

    @GetMapping("/active")
    public List<WorkExperience> getActiveWorkExperiences() {

        LOG.debug("Get all active workExperiences. ");
        return workExperienceManager.getActiveWorkExperiences();
    }

    @PostMapping(value = "/")
    public WorkExperience create(@RequestBody final WorkExperience workExperience) {

        LOG.debug("Create WorkExperience {} ", workExperience);
        return workExperienceManager.create(workExperience);
    }

    @PostMapping(value = "/bulk")
    public List<WorkExperience> create(@RequestBody final List<WorkExperience> workExperiences) {

        LOG.debug("Create WorkExperiences: {} ", workExperiences);
        return workExperienceManager.create(workExperiences);
    }

    @PutMapping(value = "/")
    public WorkExperience update(@RequestBody final WorkExperience workExperience) {

        LOG.debug("Update WorkExperience: {} ", workExperience);
        return workExperienceManager.update(workExperience);
    }

    @DeleteMapping(value = "/{workExperienceId}")
    public void delete(@PathVariable("workExperienceId") final String workExperienceId) {

        LOG.debug("Delete WorkExperience by id {} ", workExperienceId);
        workExperienceManager.delete(workExperienceId);
    }

}
