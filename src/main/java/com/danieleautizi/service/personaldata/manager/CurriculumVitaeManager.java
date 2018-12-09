package com.danieleautizi.service.personaldata.manager;

import com.danieleautizi.service.personaldata.model.presentation.CurriculumVitae;

import java.util.List;

/**
 * Manager for {@link CurriculumVitae} operations.
 */
public interface CurriculumVitaeManager {

    /**
     * Find a CurriculumVitae by its id
     * @param cvId - CurriculumVitae id
     * @return CurriculumVitae
     */
    CurriculumVitae getCurriculumVitaeById(final String cvId);

    /**
     * Find last inserted active CurriculumVitae
     * @return CurriculumVitae
     */
    CurriculumVitae getLastCurriculumVitae();

    /**
     * Find all CurriculaVitae
     * @return List<CurriculumVitae>
     */
    List<CurriculumVitae> getCurriculaVitae();

    /**
     * Find all the active CurriculaVitae
     * @return List<CurriculumVitae>
     */
    List<CurriculumVitae> getActiveCurriculaVitae();

    /**
     * Create a new CurriculumVitae
     * @param cv
     * @return CurriculumVitae created
     */
    CurriculumVitae create(final CurriculumVitae cv);

    /**
     * Update a CurriculumVitae
     * @param cv
     * @return CurriculumVitae updated
     */
    CurriculumVitae update(final CurriculumVitae cv);

    /**
     * Delete an existing CurriculumVitae by its id
     * @param cvId
     */
    void delete(final String cvId);

}
