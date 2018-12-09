package com.danieleautizi.service.personaldata.manager;

import static com.danieleautizi.service.personaldata.utility.CurriculumVitaeConverter.entitiesToPresentation;
import static com.danieleautizi.service.personaldata.utility.CurriculumVitaeConverter.entityToPresentation;
import static com.danieleautizi.service.personaldata.utility.CurriculumVitaeConverter.presentationToEntity;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringToObject;

import com.danieleautizi.service.personaldata.exception.BadRequestException;
import com.danieleautizi.service.personaldata.exception.NotFoundException;
import com.danieleautizi.service.personaldata.model.presentation.CurriculumVitae;
import com.danieleautizi.service.personaldata.repository.CurriculumVitaeRepository;
import com.danieleautizi.service.personaldata.utility.PersonalDataUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurriculumVitaeManagerImpl implements CurriculumVitaeManager {

    @NonNull
    private CurriculumVitaeRepository curriculumVitaeRepository;

    @NonNull
    private final PersonalDataUtil personalDataUtil;

    public static final String CV_NOT_FOUND_MESSAGE = "curriculumVitae.error.notfound";
    public static final String CV_BAD_REQUEST_MESSAGE = "curriculumVitae.error.badrequest";
    public static final String CV_EMPTY_MESSAGE = "curriculumVitae.error.empty";

    /**
     * Find an CurriculumVitae by its id
     * @param cvId
     * @return CurriculumVitae
     */
    @Override
    public CurriculumVitae getCurriculumVitaeById(final String cvId) {

        return entityToPresentation(curriculumVitaeRepository.findOne(stringToObject(cvId)));
    }

    @Override
    public CurriculumVitae getLastCurriculumVitae() {

        val curriculaVitae = curriculumVitaeRepository.findCurriculumVitaeByActiveOrderByPrgDesc(true);
        if (CollectionUtils.isEmpty(curriculaVitae)) {

            throw new BadRequestException(personalDataUtil.getMessage(CV_EMPTY_MESSAGE));
        }

        return entityToPresentation(curriculaVitae.get(0));
    }

    /**
     * Find all CurriculaVitae
     * @return List<CurriculumVitae>
     */
    @Override
    public List<CurriculumVitae> getCurriculaVitae() {

        return entitiesToPresentation(curriculumVitaeRepository.findAll());
    }

    /**
     * Find all active CurriculaVitae
     * @return List<CurriculumVitae>
     */
    @Override
    public List<CurriculumVitae> getActiveCurriculaVitae() {

        return entitiesToPresentation(curriculumVitaeRepository.findCurriculumVitaeByActiveOrderByPrgDesc(true));
    }

    /**
     * Create a new CurriculumVitae
     * @param cv
     * @return CurriculumVitae created
     */
    @Override
    public CurriculumVitae create(final CurriculumVitae cv) {

        val cvEntity = presentationToEntity(cv);
        return entityToPresentation(curriculumVitaeRepository.save(cvEntity));
    }

    /**
     * Update a CurriculumVitae
     * @param cv
     * @return CurriculumVitae updated
     */
    public CurriculumVitae update(final CurriculumVitae cv) {

        if (cv == null) {

            throw new BadRequestException(personalDataUtil.getMessage(CV_BAD_REQUEST_MESSAGE, cv));
        }

        if (getCurriculumVitaeById(cv.getId()) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(CV_NOT_FOUND_MESSAGE, cv.getId()));
        }

        val educationEntity = presentationToEntity(cv);
        return entityToPresentation(curriculumVitaeRepository.save(educationEntity));
    }

    /**
     * Delete an existing CurriculumVitae by its id
     * @param cvId
     */
    public void delete(final String cvId) {

        if (getCurriculumVitaeById(cvId) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(CV_NOT_FOUND_MESSAGE, cvId));
        }

        curriculumVitaeRepository.delete(stringToObject(cvId));
    }

}
