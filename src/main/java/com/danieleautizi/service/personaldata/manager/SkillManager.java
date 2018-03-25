package com.danieleautizi.service.personaldata.manager;

import com.danieleautizi.service.personaldata.model.presentation.Skill;

import java.util.List;

/**
 * Manager for {@link Skill} operations.
 */
public interface SkillManager {

    /**
     * Find an Skill by its id
     * @param skillId
     * @return Skill
     */
    Skill getSkillById(final String skillId);

    /**
     * Find Skills by a bunch of ids
     * @param skillIds
     * @return List<Skill>
     */
    List<Skill> getSkillByIds(final List<String> skillIds);

    /**
     * Find all Skills
     * @return List<Skill>
     */
    List<Skill> getSkills();

    /**
     * Find all the active Skills
     * @return List<Skill>
     */
    List<Skill> getActiveSkills();

    /**
     * Create a new Skill
     * @param skill
     * @return Skill created
     */
    Skill create(final Skill skill);

    /**
     * Create new Skills
     * @param skills
     * @return List<Skill> created
     */
    List<Skill> create(final List<Skill> skills);

    /**
     * Update an Skill
     * @param skill
     * @return Skill updated
     */
    Skill update(final Skill skill);

    /**
     * Delete an existing Skill by its id
     * @param skillId
     */
    void delete(final String skillId);

}
