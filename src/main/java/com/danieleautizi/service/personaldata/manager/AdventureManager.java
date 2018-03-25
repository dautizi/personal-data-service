package com.danieleautizi.service.personaldata.manager;

import com.danieleautizi.service.personaldata.model.presentation.Adventure;

import java.util.List;

/**
 * Manager for {@link Adventure} operations.
 */
public interface AdventureManager {

    /**
     * Find an Adventure by its id
     * @param adventureId
     * @return Adventure
     */
    Adventure getAdventureById(final String adventureId);

    /**
     * Find Adventures by a bunch of ids
     * @param adventureIds
     * @return List<Adventure>
     */
    List<Adventure> getAdventureByIds(final List<String> adventureIds);

    /**
     * Find all Adventures
     * @return List<Adventure>
     */
    List<Adventure> getAdventures();

    /**
     * Find all the active Adventures
     * @return List<Adventure>
     */
    List<Adventure> getActiveAdventures();

    /**
     * Create a new Adventure
     * @param adventure
     * @return Adventure created
     */
    Adventure create(final Adventure adventure);

    /**
     * Create new Adventures
     * @param adventures
     * @return List<Adventure> created
     */
    List<Adventure> create(final List<Adventure> adventures);

    /**
     * Update an Adventure
     * @param adventure
     * @return Adventure updated
     */
    Adventure update(final Adventure adventure);

    /**
     * Delete an existing Adventure by its id
     * @param adventureId
     */
    void delete(final String adventureId);

}
