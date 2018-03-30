package com.danieleautizi.service.personaldata.utility;

import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.localDateTimeOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.objectIdOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringOrNull;

import com.danieleautizi.service.personaldata.model.presentation.Education;

import lombok.experimental.UtilityClass;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter between {@link Education} and {@link com.danieleautizi.service.personaldata.model.entity.Education}.
 */
@UtilityClass
public class EducationConverter {

    public static com.danieleautizi.service.personaldata.model.entity.Education presentationToEntity(final Education education) {

        return education == null
               ? null
               : com.danieleautizi.service.personaldata.model.entity.Education.builder()
                                                                              .id(objectIdOrNull(education.getId()))
                                                                              .school(education.getSchool())
                                                                              .title(education.getTitle())
                                                                              .description(education.getDescription())
                                                                              .schoolImage(education.getSchoolImage())
                                                                              .schoolThumb(education.getSchoolThumb())
                                                                              .startYear(education.getStartYear())
                                                                              .endYear(education.getEndYear())

                                                                              .active(education.isActive())
                                                                              .prg(education.getPrg())

                                                                              .datetime(localDateTimeOrNull(education.getDatetime()))
                                                                              .lastUpdate(localDateTimeOrNull(education.getLastUpdate()))
                                                                              .build();
    }

    public static Education entityToPresentation(final com.danieleautizi.service.personaldata.model.entity.Education educationEntity) {

        return educationEntity == null
               ? null
               : Education.builder()
                          .id(stringOrNull(educationEntity.getId()))
                          .school(educationEntity.getSchool())
                          .title(educationEntity.getTitle())
                          .description(educationEntity.getDescription())
                          .schoolImage(educationEntity.getSchoolImage())
                          .schoolThumb(educationEntity.getSchoolThumb())
                          .startYear(educationEntity.getStartYear())
                          .endYear(educationEntity.getEndYear())

                          .active(educationEntity.isActive())
                          .prg(educationEntity.getPrg())

                          .datetime(educationEntity.getDatetime()
                                                   .atZone(ZoneOffset.UTC))
                          .lastUpdate(educationEntity.getLastUpdate()
                                                     .atZone(ZoneOffset.UTC))
                          .build();
    }

    public static List<Education> entitiesToPresentation(final List<com.danieleautizi.service.personaldata.model.entity.Education> educationEntities) {

        return educationEntities == null
               ? Collections.EMPTY_LIST
               : educationEntities.stream()
                                .map(n -> entityToPresentation(n))
                                .collect(Collectors.toList());
    }
}
