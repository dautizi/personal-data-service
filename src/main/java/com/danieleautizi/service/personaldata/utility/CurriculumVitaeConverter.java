package com.danieleautizi.service.personaldata.utility;

import static com.danieleautizi.service.personaldata.utility.MongoUtils.objectIdOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringOrNull;

import com.danieleautizi.service.personaldata.model.presentation.CurriculumVitae;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter between {@link CurriculumVitae} and {@link com.danieleautizi.service.personaldata.model.entity.CurriculumVitae}.
 */
@UtilityClass
public class CurriculumVitaeConverter {

    public static com.danieleautizi.service.personaldata.model.entity.CurriculumVitae presentationToEntity(final CurriculumVitae cv) {

        return cv == null
               ? null
               : com.danieleautizi.service.personaldata.model.entity.CurriculumVitae.builder()
                                                                                    .id(objectIdOrNull(cv.getId()))
                                                                                    .title(cv.getTitle())
                                                                                    .path(cv.getPath())
                                                                                    .filename(cv.getFilename())
                                                                                    .description(cv.getDescription())
                                                                                    .active(cv.isActive())
                                                                                    .prg(cv.getPrg())
                                                                                    .build();
    }

    public static CurriculumVitae entityToPresentation(final com.danieleautizi.service.personaldata.model.entity.CurriculumVitae cvEntity) {

        return cvEntity == null
               ? null
               : CurriculumVitae.builder()
                                .id(stringOrNull(cvEntity.getId()))
                                .title(cvEntity.getTitle())
                                .path(cvEntity.getPath())
                                .filename(cvEntity.getFilename())
                                .description(cvEntity.getDescription())
                                .active(cvEntity.isActive())
                                .prg(cvEntity.getPrg())
                                .build();
    }

    public static List<CurriculumVitae> entitiesToPresentation(final List<com.danieleautizi.service.personaldata.model.entity.CurriculumVitae> cvEntities) {

        return cvEntities == null
               ? Collections.EMPTY_LIST
               : cvEntities.stream()
                           .map(n -> entityToPresentation(n))
                           .collect(Collectors.toList());
    }
}
