package com.danieleautizi.service.personaldata.manager;

import com.danieleautizi.service.personaldata.model.presentation.AdventureMedia;

import java.util.List;

/**
 * Manager for {@link AdventureMedia} operations.
 */
public interface AdventureMediaManager {

    /**
     * Find an Adventure Media by its id
     * @param adventureMediaId
     * @return AdventureMedia
     */
    AdventureMedia getAdventureMediaById(final String adventureMediaId);

    /**
     * Find Adventure Media by a bunch of ids
     * @param adventureMediaIds
     * @return List<AdventureMedia>
     */
    List<AdventureMedia> getAdventureMediaByIds(final List<String> adventureMediaIds);

    /**
     * Find an adventureMedia by its path
     * @param adventureMediaPath
     * @return AdventureMedia
     */
    AdventureMedia getAdventureMediaByPath(final String adventureMediaPath);

    /**
     * Find all Adventure Media
     * @return List<AdventureMedia>
     */
    List<AdventureMedia> getAdventureMedia();

    /**
     * Find all the active Adventure Media
     * @return List<AdventureMedia>
     */
    List<AdventureMedia> getActiveAdventureMedia();

    /**
     * Create a new Adventure Media
     * @param adventureMedia
     * @return AdventureMedia created
     */
    AdventureMedia create(final AdventureMedia adventureMedia);

    /**
     * Create new Adventure Media
     * @param adventureMedia
     * @return List<AdventureMedia> created
     */
    List<AdventureMedia> create(final List<AdventureMedia> adventureMedia);

    /**
     * Update an Adventure Media
     * @param adventureMedia
     * @return AdventureMedia updated
     */
    AdventureMedia update(final AdventureMedia adventureMedia);

    /**
     * Delete an existing Adventure Media by its id
     * @param adventureMediaId
     */
    void delete(final String adventureMediaId);

}
