package com.danieleautizi.service.personaldata.manager;

import static com.danieleautizi.service.personaldata.utility.AdventureMediaConverter.entitiesToPresentation;
import static com.danieleautizi.service.personaldata.utility.AdventureMediaConverter.entityToPresentation;
import static com.danieleautizi.service.personaldata.utility.AdventureMediaConverter.presentationToEntity;
import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.utcLocalDateTimeNow;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringToObject;

import com.danieleautizi.service.personaldata.exception.BadRequestException;
import com.danieleautizi.service.personaldata.exception.NotFoundException;
import com.danieleautizi.service.personaldata.model.presentation.AdventureMedia;
import com.danieleautizi.service.personaldata.repository.AdventureMediaRepository;
import com.danieleautizi.service.personaldata.utility.PersonalDataUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdventureMediaManagerImpl implements AdventureMediaManager {

    @NonNull
    private AdventureMediaRepository adventureMediaRepository;

    @NonNull
    private final PersonalDataUtil personalDataUtil;

    public static final String ADVENTURE_MEDIA_NOT_FOUND_MESSAGE = "adventuremedia.error.notfound";
    public static final String ADVENTURE_MEDIA_BAD_REQUEST_MESSAGE = "adventuremedia.error.badrequest";

    /**
     * Find an AdventureMedia by its id
     * @param adventureMediaId
     * @return AdventureMedia
     */
    @Override
    public AdventureMedia getAdventureMediaById(final String adventureMediaId) {

        return entityToPresentation(adventureMediaRepository.findOne(stringToObject(adventureMediaId)));
    }

    /**
     * Find AdventureMedia by a bunch of ids
     * @param adventureMediaIds
     * @return List<AdventureMedia>
     */
    @Override
    public List<AdventureMedia> getAdventureMediaByIds(final List<String> adventureMediaIds) {

        return entitiesToPresentation(adventureMediaRepository.findAdventureMediaByIdIn(stringToObject(adventureMediaIds)));
    }

    /**
     * Find an Adventure Media by its path
     * @param adventureMediaPath
     * @return AdventureMedia
     */
    public AdventureMedia getAdventureMediaByPath(final String adventureMediaPath) {

        return entityToPresentation(adventureMediaRepository.findAdventureMediaByMediaPath(adventureMediaPath));
    }

    /**
     * Find all Adventure Media
     * @return List<AdventureMedia>
     */
    @Override
    public List<AdventureMedia> getAdventureMedia() {

        return entitiesToPresentation(adventureMediaRepository.findAll());
    }

    /**
     * Find all the active Adventure Media
     * @return List<AdventureMedia>
     */
    @Override
    public List<AdventureMedia> getActiveAdventureMedia() {

        return entitiesToPresentation(adventureMediaRepository.findAdventureMediaByActive(true));
    }

    /**
     * Create a new AdventureMedia
     * @param adventureMedia
     * @return AdventureMedia created
     */
    @Override
    public AdventureMedia create(final AdventureMedia adventureMedia) {

        val now = utcLocalDateTimeNow();
        val adventureMediaEntity = presentationToEntity(adventureMedia);
        adventureMediaEntity.setDatetime(now);
        adventureMediaEntity.setLastUpdate(now);
        return entityToPresentation(adventureMediaRepository.save(adventureMediaEntity));
    }

    /**
     * Create new Adventure Media
     * @param adventureMedia
     * @return List<AdventureMedia> created
     */
    public List<AdventureMedia> create(final List<AdventureMedia> adventureMedia) {

        return adventureMedia.stream()
                             .map(am -> create(am))
                             .collect(Collectors.toList());
    }

    /**
     * Update an Adventure Media
     * @param adventureMedia
     * @return AdventureMedia updated
     */
    public AdventureMedia update(final AdventureMedia adventureMedia) {

        if (adventureMedia == null) {

            throw new BadRequestException(personalDataUtil.getMessage(ADVENTURE_MEDIA_BAD_REQUEST_MESSAGE, adventureMedia));
        }

        if (getAdventureMediaById(adventureMedia.getId()) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(ADVENTURE_MEDIA_NOT_FOUND_MESSAGE, adventureMedia.getId()));
        }

        val adventureMediaEntity = presentationToEntity(adventureMedia);
        adventureMediaEntity.setLastUpdate(utcLocalDateTimeNow());
        return entityToPresentation(adventureMediaRepository.save(adventureMediaEntity));
    }

    /**
     * Delete an existing Adventure Media by its id
     * @param adventureMediaId
     */
    public void delete(final String adventureMediaId) {

        if (getAdventureMediaById(adventureMediaId) == null) {

            throw new NotFoundException(personalDataUtil.getMessage(ADVENTURE_MEDIA_NOT_FOUND_MESSAGE, adventureMediaId));
        }

        adventureMediaRepository.delete(stringToObject(adventureMediaId));
    }

}
