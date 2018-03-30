package com.danieleautizi.service.personaldata.utility;

import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.localDateTimeOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.objectIdOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringOrNull;

import com.danieleautizi.service.personaldata.model.presentation.WorkExperience;

import lombok.experimental.UtilityClass;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter between {@link WorkExperience} and {@link com.danieleautizi.service.personaldata.model.entity.WorkExperience}.
 */
@UtilityClass
public class WorkExperienceConverter {

    public static com.danieleautizi.service.personaldata.model.entity.WorkExperience presentationToEntity(final WorkExperience workExperience) {

        return workExperience == null
               ? null
               : com.danieleautizi.service.personaldata.model.entity.WorkExperience.builder()
                                                                                   .id(objectIdOrNull(workExperience.getId()))
                                                                                   .company(workExperience.getCompany())
                                                                                   .role(workExperience.getRole())
                                                                                   .description(workExperience.getDescription())
                                                                                   .period(workExperience.getPeriod())
                                                                                   .companyImage(workExperience.getCompanyImage())
                                                                                   .companyThumb(workExperience.getCompanyThumb())
                                                                                   .startYear(workExperience.getStartYear())
                                                                                   .endYear(workExperience.getEndYear())

                                                                                   .active(workExperience.isActive())
                                                                                   .prg(workExperience.getPrg())

                                                                                   .datetime(localDateTimeOrNull(workExperience.getDatetime()))
                                                                                   .lastUpdate(localDateTimeOrNull(workExperience.getLastUpdate()))
                                                                                   .build();
    }

    public static WorkExperience entityToPresentation(final com.danieleautizi.service.personaldata.model.entity.WorkExperience workExperienceEntity) {

        return workExperienceEntity == null
               ? null
               : WorkExperience.builder()
                               .id(stringOrNull(workExperienceEntity.getId()))
                               .company(workExperienceEntity.getCompany())
                               .role(workExperienceEntity.getRole())
                               .description(workExperienceEntity.getDescription())
                               .period(workExperienceEntity.getPeriod())
                               .companyImage(workExperienceEntity.getCompanyImage())
                               .companyThumb(workExperienceEntity.getCompanyThumb())
                               .startYear(workExperienceEntity.getStartYear())
                               .endYear(workExperienceEntity.getEndYear())

                               .active(workExperienceEntity.isActive())
                               .prg(workExperienceEntity.getPrg())

                               .datetime(workExperienceEntity.getDatetime()
                                                             .atZone(ZoneOffset.UTC))
                               .lastUpdate(workExperienceEntity.getLastUpdate()
                                                               .atZone(ZoneOffset.UTC))
                               .build();
    }

    public static List<WorkExperience> entitiesToPresentation(final List<com.danieleautizi.service.personaldata.model.entity.WorkExperience> workExperienceEntities) {

        return workExperienceEntities == null
               ? Collections.EMPTY_LIST
               : workExperienceEntities.stream()
                                .map(n -> entityToPresentation(n))
                                .collect(Collectors.toList());
    }
}
