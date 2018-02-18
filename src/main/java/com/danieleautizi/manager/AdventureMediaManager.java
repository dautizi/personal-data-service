package com.danieleautizi.manager;

import com.danieleautizi.model.presentation.AdventureMedia;

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
    public AdventureMedia getAdventureMediaById(final String adventureMediaId);

    /**
     * Find Adventure Media by a bunch of ids
     * @param adventureMediaIds
     * @return List<AdventureMedia>
     */
    public List<AdventureMedia> getAdventureMediaByIds(final List<String> adventureMediaIds);

    /**
     * Find all Adventure Media
     * @return List<AdventureMedia>
     */
    public List<AdventureMedia> getAdventureMedia();

    /**
     * Find all the active Adventure Media
     * @return List<AdventureMedia>
     */
    public List<AdventureMedia> getActiveAdventureMedia();

    /**
     * Create a new Adventure Media
     * @param adventureMedia
     * @return AdventureMedia created
     */
    public AdventureMedia create(final AdventureMedia adventureMedia);

    /**
     * Create new Adventure Media
     * @param adventureMedia
     * @return List<AdventureMedia> created
     */
    public List<AdventureMedia> create(final List<AdventureMedia> adventureMedia);

    /**
     * Update an Adventure Media
     * @param adventureMedia
     * @return AdventureMedia updated
     */
    public AdventureMedia update(final AdventureMedia adventureMedia);

    /**
     * Delete an existing Adventure Media by its id
     * @param adventureMediaId
     */
    public void delete(final String adventureMediaId);

}
