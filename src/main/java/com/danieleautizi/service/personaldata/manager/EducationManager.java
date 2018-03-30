package com.danieleautizi.service.personaldata.manager;

import com.danieleautizi.service.personaldata.model.presentation.Education;

import java.util.List;

/**
 * Manager for {@link Education} operations.
 */
public interface EducationManager {

    /**
     * Find an Education by its id
     * @param educationId
     * @return Education
     */
    Education getEducationById(final String educationId);

    /**
     * Find Educations by a bunch of ids
     * @param educationIds
     * @return List<Education>
     */
    List<Education> getEducationByIds(final List<String> educationIds);

    /**
     * Find all Educations
     * @return List<Education>
     */
    List<Education> getEducations();

    /**
     * Find all the active Educations
     * @return List<Education>
     */
    List<Education> getActiveEducations();

    /**
     * Create a new Education
     * @param education
     * @return Education created
     */
    Education create(final Education education);

    /**
     * Create new Educations
     * @param educations
     * @return List<Education> created
     */
    List<Education> create(final List<Education> educations);

    /**
     * Update an Education
     * @param education
     * @return Education updated
     */
    Education update(final Education education);

    /**
     * Delete an existing Education by its id
     * @param educationId
     */
    void delete(final String educationId);

}
