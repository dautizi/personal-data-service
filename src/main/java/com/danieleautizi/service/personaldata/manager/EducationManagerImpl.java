package com.danieleautizi.service.personaldata.manager;

import static com.danieleautizi.service.personaldata.utility.EducationConverter.entitiesToPresentation;
import static com.danieleautizi.service.personaldata.utility.EducationConverter.entityToPresentation;
import static com.danieleautizi.service.personaldata.utility.EducationConverter.presentationToEntity;
import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.utcZonedLocalDateTimeNow;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringToObject;

import com.danieleautizi.service.personaldata.exception.BadRequestException;
import com.danieleautizi.service.personaldata.exception.NotFoundException;
import com.danieleautizi.service.personaldata.model.presentation.Education;
import com.danieleautizi.service.personaldata.repository.EducationRepository;
import com.danieleautizi.service.personaldata.utility.PersonalDataUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationManagerImpl implements EducationManager {

    @NonNull
    private EducationRepository educationRepository;

    @NonNull
    private final PersonalDataUtil personalDataUtil;

    public static final String EDUCATION_NOT_FOUND_MESSAGE = "education.error.notfound";
    public static final String EDUCATION_BAD_REQUEST_MESSAGE = "education.error.badrequest";

    /**
     * Find an Education by its id
     * @param educationId
     * @return Education
     */
    @Override
    public Education getEducationById(final String educationId) {

        return entityToPresentation(educationRepository.findOne(stringToObject(educationId)));
    }

    /**
     * Find Educations by a bunch of ids
     * @param educationIds
     * @return List<Education>
     */
    @Override
    public List<Education> getEducationByIds(final List<String> educationIds) {

        return entitiesToPresentation(educationRepository.findEducationsByIdIn(stringToObject(educationIds)));
    }

    /**
     * Find all Educations
     * @return List<Education>
     */
    @Override
    public List<Education> getEducations() {

        return entitiesToPresentation(educationRepository.findAll());
    }

    /**
     * Find all active Educations
     * @return List<Education>
     */
    @Override
    public List<Education> getActiveEducations() {

        return entitiesToPresentation(educationRepository.findEducationsByActive(true));
    }

    /**
     * Create a new Education
     * @param education
     * @return Education created
     */
    @Override
    public Education create(final Education education) {

        val now = utcZonedLocalDateTimeNow();
        education.setDatetime(now);
        education.setLastUpdate(now);
        val educationEntity = presentationToEntity(education);

        return entityToPresentation(educationRepository.save(educationEntity));
    }

    /**
     * Create new Educations
     * @param educations
     * @return List<Education> created
     */
    public List<Education> create(final List<Education> educations) {

        return educations.stream()
                         .map(education -> create(education))
                         .collect(Collectors.toList());
    }

    /**
     * Update an Education
     * @param education
     * @return Education updated
     */
    public Education update(final Education education) {

        if (education == null) {

            throw new BadRequestException(personalDataUtil.getMessage(EDUCATION_BAD_REQUEST_MESSAGE, education));
        }

        if (getEducationById(education.getId()) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(EDUCATION_NOT_FOUND_MESSAGE, education.getId()));
        }

        education.setLastUpdate(utcZonedLocalDateTimeNow());
        val educationEntity = presentationToEntity(education);

        return entityToPresentation(educationRepository.save(educationEntity));
    }

    /**
     * Delete an existing Education by its id
     * @param educationId
     */
    public void delete(final String educationId) {

        if (getEducationById(educationId) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(EDUCATION_NOT_FOUND_MESSAGE, educationId));
        }

        educationRepository.delete(stringToObject(educationId));
    }

}
