package com.danieleautizi.controller;

import com.danieleautizi.manager.SkillManager;
import com.danieleautizi.model.presentation.Skill;

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
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillManager skillManager;

    @GetMapping(value = "/{skillId}")
    public Skill getSkill(@PathVariable("skillId") final String skillId) {

        LOG.debug("Get skill by id {} ", skillId);
        return skillManager.getSkillById(skillId);
    }

    @GetMapping("/")
    public List<Skill> getSkills() {

        LOG.debug("Get all skills. ");
        return skillManager.getSkills();
    }

    @GetMapping("/active")
    public List<Skill> getActiveSkills() {

        LOG.debug("Get all active skills. ");
        return skillManager.getActiveSkills();
    }

    @PostMapping(value = "/")
    public Skill create(@RequestBody final Skill skill) {

        LOG.debug("Create Skill {} ", skill);
        return skillManager.create(skill);
    }

    @PostMapping(value = "/bulk")
    public List<Skill> create(@RequestBody final List<Skill> skills) {

        LOG.debug("Create Skills: {} ", skills);
        return skillManager.create(skills);
    }

    @PutMapping(value = "/")
    public void update(@RequestBody final Skill skill) {

        LOG.debug("Update Skill: {} ", skill);
        skillManager.update(skill);
    }

    @DeleteMapping(value = "/{skillId}")
    public void delete(@PathVariable("skillId") final String skillId) {

        LOG.debug("Delete Skill by id {} ", skillId);
        skillManager.delete(skillId);
    }

}
