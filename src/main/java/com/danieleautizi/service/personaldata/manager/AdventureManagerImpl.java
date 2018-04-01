package com.danieleautizi.service.personaldata.manager;

import static com.danieleautizi.service.personaldata.utility.AdventureConverter.entitiesToPresentation;
import static com.danieleautizi.service.personaldata.utility.AdventureConverter.entityToPresentation;
import static com.danieleautizi.service.personaldata.utility.AdventureConverter.presentationToEntity;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringToObject;
import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.utcZonedLocalDateTimeNow;

import com.danieleautizi.service.personaldata.exception.BadRequestException;
import com.danieleautizi.service.personaldata.exception.NotFoundException;
import com.danieleautizi.service.personaldata.model.presentation.Adventure;
import com.danieleautizi.service.personaldata.repository.AdventureRepository;
import com.danieleautizi.service.personaldata.utility.PersonalDataUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdventureManagerImpl implements AdventureManager {

    @NonNull
    private AdventureRepository adventureRepository;

    @NonNull
    private final PersonalDataUtil personalDataUtil;

    public static final String ADVENTURE_NOT_FOUND_MESSAGE = "adventure.error.notfound";
    public static final String ADVENTURE_BAD_REQUEST_MESSAGE = "adventure.error.badrequest";

    /**
     * Find an Adventure by its id
     * @param adventureId
     * @return Adventure
     */
    @Override
    public Adventure getAdventureById(final String adventureId) {

        return entityToPresentation(adventureRepository.findOne(stringToObject(adventureId)));
    }

    /**
     * Find Adventures by a bunch of ids
     * @param adventureIds
     * @return List<Adventure>
     */
    @Override
    public List<Adventure> getAdventureByIds(final List<String> adventureIds) {

        return entitiesToPresentation(adventureRepository.findAdventuresByIdIn(stringToObject(adventureIds)));
    }

    /**
     * Find all Adventures
     * @return List<Adventure>
     */
    @Override
    public List<Adventure> getAdventures() {

        return entitiesToPresentation(adventureRepository.findAll());
    }

    /**
     * Find all active Adventures
     * @return List<Adventure>
     */
    @Override
    public List<Adventure> getActiveAdventures() {

        return entitiesToPresentation(adventureRepository.findAdventuresByActive(true));
    }

    /**
     * Create a new Adventure
     * @param adventure
     * @return Adventure created
     */
    @Override
    public Adventure create(final Adventure adventure) {

        val now = utcZonedLocalDateTimeNow();
        adventure.setDatetime(now);
        adventure.setLastUpdate(now);
        val adventureEntity = presentationToEntity(adventure);

        return entityToPresentation(adventureRepository.save(adventureEntity));
    }

    /**
     * Create new Adventures
     * @param adventures
     * @return List<Adventure> created
     */
    public List<Adventure> create(final List<Adventure> adventures) {

        return adventures.stream()
                         .map(adventure -> create(adventure))
                         .collect(Collectors.toList());
    }

    /**
     * Update an Adventure
     * @param adventure
     * @return Adventure updated
     */
    public Adventure update(final Adventure adventure) {

        if (adventure == null) {

            throw new BadRequestException(personalDataUtil.getMessage(ADVENTURE_BAD_REQUEST_MESSAGE, adventure));
        }

        if (getAdventureById(adventure.getId()) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(ADVENTURE_NOT_FOUND_MESSAGE, adventure.getId()));
        }

        adventure.setLastUpdate(utcZonedLocalDateTimeNow());
        val adventureEntity = presentationToEntity(adventure);

        return entityToPresentation(adventureRepository.save(adventureEntity));
    }

    /**
     * Delete an existing Adventure by its id
     * @param adventureId
     */
    public void delete(final String adventureId) {

        if (getAdventureById(adventureId) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(ADVENTURE_NOT_FOUND_MESSAGE, adventureId));
        }

        adventureRepository.delete(stringToObject(adventureId));
    }

}
