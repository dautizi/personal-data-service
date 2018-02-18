package com.danieleautizi.utility;

import static com.danieleautizi.utility.MongoUtils.objectIdOrNull;
import static com.danieleautizi.utility.MongoUtils.stringOrNull;

import com.danieleautizi.model.presentation.AdventureMedia;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter between {@link AdventureMedia} and {@link com.danieleautizi.model.entity.AdventureMedia}.
 */
@UtilityClass
public class AdventureMediaConverter {

    public static com.danieleautizi.model.entity.AdventureMedia presentationToEntity(final AdventureMedia adventureMedia) {

        return adventureMedia == null
               ? null
               : com.danieleautizi.model.entity.AdventureMedia.builder()
                                                              .id(objectIdOrNull(adventureMedia.getId()))
                                                              .mediaType(adventureMedia.getMediaType())
                                                              .mediaPath(adventureMedia.getMediaPath())
                                                              .mediaUrl(adventureMedia.getMediaUrl())
                                                              .title(adventureMedia.getTitle())
                                                              .alt(adventureMedia.getAlt())
                                                              .cssClass(adventureMedia.getCssClass())
                                                              .active(adventureMedia.isActive())
                                                              .prg(adventureMedia.getPrg())
                                                              .build();
    }

    public static AdventureMedia entityToPresentation(final com.danieleautizi.model.entity.AdventureMedia adventureMediaEntity) {

        return adventureMediaEntity == null
               ? null
               : AdventureMedia.builder()
                               .id(stringOrNull(adventureMediaEntity.getId()))
                               .mediaType(adventureMediaEntity.getMediaType())
                               .mediaPath(adventureMediaEntity.getMediaPath())
                               .mediaUrl(adventureMediaEntity.getMediaUrl())

                               .title(adventureMediaEntity.getTitle())
                               .alt(adventureMediaEntity.getAlt())
                               .cssClass(adventureMediaEntity.getCssClass())
                               .active(adventureMediaEntity.isActive())
                               .prg(adventureMediaEntity.getPrg())
                               .build();
    }

    public static List<AdventureMedia> entitiesToPresentation(final List<com.danieleautizi.model.entity.AdventureMedia> adventureMediaEntities) {

        return adventureMediaEntities == null
               ? Collections.EMPTY_LIST
               : adventureMediaEntities.stream()
                                       .map(n -> entityToPresentation(n))
                                       .collect(Collectors.toList());
    }

}
