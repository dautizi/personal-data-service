package com.danieleautizi.service.personaldata.manager;

import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringToObject;
import static com.danieleautizi.service.personaldata.utility.WorkExperienceConverter.entitiesToPresentation;
import static com.danieleautizi.service.personaldata.utility.WorkExperienceConverter.entityToPresentation;
import static com.danieleautizi.service.personaldata.utility.WorkExperienceConverter.presentationToEntity;
import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.utcZonedLocalDateTimeNow;

import com.danieleautizi.service.personaldata.exception.BadRequestException;
import com.danieleautizi.service.personaldata.exception.NotFoundException;
import com.danieleautizi.service.personaldata.model.presentation.WorkExperience;
import com.danieleautizi.service.personaldata.repository.WorkExperienceRepository;
import com.danieleautizi.service.personaldata.utility.PersonalDataUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkExperienceManagerImpl implements WorkExperienceManager {

    @NonNull
    private WorkExperienceRepository workExperienceRepository;

    @NonNull
    private final PersonalDataUtil personalDataUtil;

    public static final String WORK_EXPERIENCE_NOT_FOUND_MESSAGE = "workexperience.error.notfound";
    public static final String WORK_EXPERIENCE_BAD_REQUEST_MESSAGE = "workexperience.error.badrequest";

    /**
     * Find an WorkExperience by its id
     * @param workExperienceId
     * @return WorkExperience
     */
    @Override
    public WorkExperience getWorkExperienceById(final String workExperienceId) {

        return entityToPresentation(workExperienceRepository.findOne(stringToObject(workExperienceId)));
    }

    /**
     * Find WorkExperiences by a bunch of ids
     * @param workExperienceIds
     * @return List<WorkExperience>
     */
    @Override
    public List<WorkExperience> getWorkExperienceByIds(final List<String> workExperienceIds) {

        return entitiesToPresentation(workExperienceRepository.findWorkExperiencesByIdIn(stringToObject(workExperienceIds)));
    }

    /**
     * Find all WorkExperiences
     * @return List<WorkExperience>
     */
    @Override
    public List<WorkExperience> getWorkExperiences() {

        return entitiesToPresentation(workExperienceRepository.findAll());
    }

    /**
     * Find all active WorkExperiences
     * @return List<WorkExperience>
     */
    @Override
    public List<WorkExperience> getActiveWorkExperiences() {

        return entitiesToPresentation(workExperienceRepository.findWorkExperiencesByActive(true));
    }

    /**
     * Create a new WorkExperience
     * @param workExperience
     * @return WorkExperience created
     */
    @Override
    public WorkExperience create(final WorkExperience workExperience) {

        val now = utcZonedLocalDateTimeNow();
        workExperience.setDatetime(now);
        workExperience.setLastUpdate(now);

        val workExperienceEntity = workExperienceRepository.save(presentationToEntity(workExperience));
        return entityToPresentation(workExperienceEntity);
    }

    /**
     * Create new WorkExperiences
     * @param workExperiences
     * @return List<WorkExperience> created
     */
    public List<WorkExperience> create(final List<WorkExperience> workExperiences) {

        return workExperiences.stream()
                         .map(workExperience -> create(workExperience))
                         .collect(Collectors.toList());
    }

    /**
     * Update an WorkExperience
     * @param workExperience
     * @return WorkExperience updated
     */
    public WorkExperience update(final WorkExperience workExperience) {

        if (workExperience == null) {

            throw new BadRequestException(personalDataUtil.getMessage(WORK_EXPERIENCE_BAD_REQUEST_MESSAGE, workExperience));
        }

        if (getWorkExperienceById(workExperience.getId()) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(WORK_EXPERIENCE_NOT_FOUND_MESSAGE, workExperience.getId()));
        }

        workExperience.setLastUpdate(utcZonedLocalDateTimeNow());
        val workExperienceEntity = presentationToEntity(workExperience);
        return entityToPresentation(workExperienceRepository.save(workExperienceEntity));
    }

    /**
     * Delete an existing WorkExperience by its id
     * @param workExperienceId
     */
    public void delete(final String workExperienceId) {

        if (getWorkExperienceById(workExperienceId) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(WORK_EXPERIENCE_NOT_FOUND_MESSAGE, workExperienceId));
        }

        workExperienceRepository.delete(stringToObject(workExperienceId));
    }

}
