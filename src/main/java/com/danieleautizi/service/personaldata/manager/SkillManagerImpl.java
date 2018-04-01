package com.danieleautizi.service.personaldata.manager;

import static com.danieleautizi.service.personaldata.utility.SkillConverter.entitiesToPresentation;
import static com.danieleautizi.service.personaldata.utility.SkillConverter.entityToPresentation;
import static com.danieleautizi.service.personaldata.utility.SkillConverter.presentationToEntity;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringToObject;
import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.utcZonedLocalDateTimeNow;

import com.danieleautizi.service.personaldata.exception.BadRequestException;
import com.danieleautizi.service.personaldata.exception.NotFoundException;
import com.danieleautizi.service.personaldata.model.presentation.Skill;
import com.danieleautizi.service.personaldata.repository.SkillRepository;
import com.danieleautizi.service.personaldata.utility.PersonalDataUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillManagerImpl implements SkillManager {

    @NonNull
    private SkillRepository skillRepository;

    @NonNull
    private final PersonalDataUtil personalDataUtil;

    public static final String SKILL_NOT_FOUND_MESSAGE = "skill.error.notfound";
    public static final String SKILL_BAD_REQUEST_MESSAGE = "skill.error.badrequest";

    /**
     * Find an Skill by its id
     * @param skillId
     * @return Skill
     */
    @Override
    public Skill getSkillById(final String skillId) {

        return entityToPresentation(skillRepository.findOne(stringToObject(skillId)));
    }

    /**
     * Find Skills by a bunch of ids
     * @param skillIds
     * @return List<Skill>
     */
    @Override
    public List<Skill> getSkillByIds(final List<String> skillIds) {

        return entitiesToPresentation(skillRepository.findSkillsByIdIn(stringToObject(skillIds)));
    }

    /**
     * Find all Skills
     * @return List<Skill>
     */
    @Override
    public List<Skill> getSkills() {

        return entitiesToPresentation(skillRepository.findAll());
    }

    /**
     * Find all active Skills
     * @return List<Skill>
     */
    @Override
    public List<Skill> getActiveSkills() {

        return entitiesToPresentation(skillRepository.findSkillsByActive(true));
    }

    /**
     * Create a new Skill
     * @param skill
     * @return Skill created
     */
    @Override
    public Skill create(final Skill skill) {

        val now = utcZonedLocalDateTimeNow();
        skill.setDatetime(now);
        skill.setLastUpdate(now);
        val skillEntity = skillRepository.save(presentationToEntity(skill));

        return entityToPresentation(skillEntity);
    }

    /**
     * Create new Skills
     * @param skills
     * @return List<Skill> created
     */
    public List<Skill> create(final List<Skill> skills) {

        return skills.stream()
                         .map(skill -> create(skill))
                         .collect(Collectors.toList());
    }

    /**
     * Update an Skill
     * @param skill
     * @return Skill updated
     */
    public Skill update(final Skill skill) {

        if (skill == null) {

            throw new BadRequestException(personalDataUtil.getMessage(SKILL_BAD_REQUEST_MESSAGE, skill));
        }

        if (getSkillById(skill.getId()) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(SKILL_NOT_FOUND_MESSAGE, skill.getId()));
        }

        skill.setLastUpdate(utcZonedLocalDateTimeNow());
        val skillEntity = presentationToEntity(skill);

        return entityToPresentation(skillRepository.save(skillEntity));
    }

    /**
     * Delete an existing Skill by its id
     * @param skillId
     */
    public void delete(final String skillId) {

        if (getSkillById(skillId) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(SKILL_NOT_FOUND_MESSAGE, skillId));
        }

        skillRepository.delete(stringToObject(skillId));
    }

}
