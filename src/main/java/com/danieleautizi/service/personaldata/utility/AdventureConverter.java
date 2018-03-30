package com.danieleautizi.service.personaldata.utility;

import static com.danieleautizi.service.personaldata.utility.MongoUtils.objectIdOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringOrNull;
import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.localDateTimeOrNull;

import com.danieleautizi.service.personaldata.model.presentation.Adventure;
import com.danieleautizi.service.personaldata.model.presentation.AdventureMedia;

import lombok.experimental.UtilityClass;

import org.bson.types.ObjectId;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter between {@link Adventure} and {@link com.danieleautizi.service.personaldata.model.entity.Adventure}.
 */
@UtilityClass
public class AdventureConverter {

    public static com.danieleautizi.service.personaldata.model.entity.Adventure presentationToEntity(final Adventure adventure) {

        return adventure == null
               ? null
               : com.danieleautizi.service.personaldata.model.entity.Adventure.builder()
                                                                              .id(objectIdOrNull(adventure.getId()))
                                                                              .articleUniquePath(adventure.getArticleUniquePath())
                                                                              .title(adventure.getTitle())
                                                                              .category(adventure.getCategory())
                                                                              .section(adventure.getSection())
                                                                              .tag(adventure.getTag())

                                                                              .keywords(adventure.getKeywords())
                                                                              .cssClass(adventure.getCssClass())
                                                                              .image(adventure.getImage())
                                                                              .icon(adventure.getIcon())
                                                                              .altImage(adventure.getAltImage())
                                                                              .articleUrl(adventure.getArticleUrl())
                                                                              .description(adventure.getDescription())
                                                                              .adventureType(adventure.getAdventureType())

                                                                              .staticUrl(adventure.getStaticUrl())
                                                                              .viewType(adventure.getViewType())
                                                                              .mediaCssClass(adventure.getMediaCssClass())
                                                                              .active(adventure.isActive())
                                                                              .prg(adventure.getPrg())

                                                                              .datetime(localDateTimeOrNull(adventure.getDatetime()))
                                                                              .lastUpdate(localDateTimeOrNull(adventure.getLastUpdate()))

                                                                              .adventureMediaIds(getAdventureMediaIds(adventure.getAdventureMedia()))
                                                                              .build();
    }


    public static Adventure entityToPresentation(final com.danieleautizi.service.personaldata.model.entity.Adventure adventureEntity) {

        return adventureEntity == null
               ? null
               : Adventure.builder()
                          .id(stringOrNull(adventureEntity.getId()))
                          .articleUniquePath(adventureEntity.getArticleUniquePath())
                          .title(adventureEntity.getTitle())
                          .category(adventureEntity.getCategory())
                          .section(adventureEntity.getSection())
                          .tag(adventureEntity.getTag())

                          .keywords(adventureEntity.getKeywords())
                          .cssClass(adventureEntity.getCssClass())
                          .image(adventureEntity.getImage())
                          .icon(adventureEntity.getIcon())
                          .altImage(adventureEntity.getAltImage())
                          .articleUrl(adventureEntity.getArticleUrl())
                          .description(adventureEntity.getDescription())
                          .adventureType(adventureEntity.getAdventureType())

                          .staticUrl(adventureEntity.getStaticUrl())
                          .viewType(adventureEntity.getViewType())
                          .mediaCssClass(adventureEntity.getMediaCssClass())
                          .active(adventureEntity.isActive())
                          .prg(adventureEntity.getPrg())

                          .datetime(adventureEntity.getDatetime()
                                                   .atZone(ZoneOffset.UTC))
                          .lastUpdate(adventureEntity.getLastUpdate()
                                                     .atZone(ZoneOffset.UTC))
                          .adventureMedia(getAdventureMediaList(adventureEntity.getAdventureMediaIds()))
                          .build();
    }

    public static List<Adventure> entitiesToPresentation(final List<com.danieleautizi.service.personaldata.model.entity.Adventure> adventureEntities) {

        return adventureEntities == null
               ? Collections.EMPTY_LIST
               : adventureEntities.stream()
                                  .map(n -> entityToPresentation(n))
                                  .collect(Collectors.toList());
    }

    private static List<ObjectId> getAdventureMediaIds(final List<AdventureMedia> adventureMediaList) {

        return adventureMediaList == null
               ? Collections.emptyList()
               : adventureMediaList.stream()
                                   .map(adventureMedia -> objectIdOrNull(adventureMedia.getId()))
                                   .collect(Collectors.toList());
    }

    private static List<AdventureMedia> getAdventureMediaList(final List<ObjectId> adventureMediaIds) {

        return adventureMediaIds == null
               ? Collections.emptyList()
               : adventureMediaIds.stream()
                                  .map(adventureMediaId -> AdventureMedia.builder()
                                                                         .id(stringOrNull(adventureMediaId))
                                                                         .build())
                                  .collect(Collectors.toList());
    }
}
