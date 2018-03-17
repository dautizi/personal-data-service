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
    public Skill getSkillById(final String skillId);

    /**
     * Find Skills by a bunch of ids
     * @param skillIds
     * @return List<Skill>
     */
    public List<Skill> getSkillByIds(final List<String> skillIds);

    /**
     * Find all Skills
     * @return List<Skill>
     */
    public List<Skill> getSkills();

    /**
     * Find all the active Skills
     * @return List<Skill>
     */
    public List<Skill> getActiveSkills();

    /**
     * Create a new Skill
     * @param skill
     * @return Skill created
     */
    public Skill create(final Skill skill);

    /**
     * Create new Skills
     * @param skills
     * @return List<Skill> created
     */
    public List<Skill> create(final List<Skill> skills);

    /**
     * Update an Skill
     * @param skill
     * @return Skill updated
     */
    public Skill update(final Skill skill);

    /**
     * Delete an existing Skill by its id
     * @param skillId
     */
    public void delete(final String skillId);

}
