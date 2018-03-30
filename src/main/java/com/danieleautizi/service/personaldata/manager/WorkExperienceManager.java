package com.danieleautizi.service.personaldata.manager;

import com.danieleautizi.service.personaldata.model.presentation.WorkExperience;

import java.util.List;

/**
 * Manager for {@link WorkExperience} operations.
 */
public interface WorkExperienceManager {

    /**
     * Find an WorkExperience by its id
     * @param workExperienceId
     * @return WorkExperience
     */
    WorkExperience getWorkExperienceById(final String workExperienceId);

    /**
     * Find WorkExperiences by a bunch of ids
     * @param workExperienceIds
     * @return List<WorkExperience>
     */
    List<WorkExperience> getWorkExperienceByIds(final List<String> workExperienceIds);

    /**
     * Find all WorkExperiences
     * @return List<WorkExperience>
     */
    List<WorkExperience> getWorkExperiences();

    /**
     * Find all the active WorkExperiences
     * @return List<WorkExperience>
     */
    List<WorkExperience> getActiveWorkExperiences();

    /**
     * Create a new WorkExperience
     * @param workExperience
     * @return WorkExperience created
     */
    WorkExperience create(final WorkExperience workExperience);

    /**
     * Create new WorkExperiences
     * @param workExperiences
     * @return List<WorkExperience> created
     */
    List<WorkExperience> create(final List<WorkExperience> workExperiences);

    /**
     * Update an WorkExperience
     * @param workExperience
     * @return WorkExperience updated
     */
    WorkExperience update(final WorkExperience workExperience);

    /**
     * Delete an existing WorkExperience by its id
     * @param workExperienceId
     */
    void delete(final String workExperienceId);

}
