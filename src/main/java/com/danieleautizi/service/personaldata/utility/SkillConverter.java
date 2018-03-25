package com.danieleautizi.service.personaldata.utility;

import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.localDateTimeOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.objectIdOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringOrNull;

import com.danieleautizi.service.personaldata.model.presentation.Skill;

import lombok.experimental.UtilityClass;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter between {@link Skill} and {@link com.danieleautizi.service.personaldata.model.entity.Skill}.
 */
@UtilityClass
public class SkillConverter {

    public static com.danieleautizi.service.personaldata.model.entity.Skill presentationToEntity(final Skill skill) {

        return skill == null
               ? null
               : com.danieleautizi.service.personaldata.model.entity.Skill.builder()
                                                                          .id(objectIdOrNull(skill.getId()))
                                                                          .groupName(skill.getGroupName())
                                                                          .title(skill.getTitle())
                                                                          .progress(skill.getProgress())
                                                                          .percent(skill.getPercent())
                                                                          .years(skill.getYears())
                                                                          .since(localDateTimeOrNull(skill.getSince()))
                                                                          .imageUrl(skill.getImageUrl())
                                                                          .active(skill.isActive())
                                                                          .prg(skill.getPrg())
                                                                          .build();
    }

    public static Skill entityToPresentation(final com.danieleautizi.service.personaldata.model.entity.Skill skillEntity) {

        return skillEntity == null
               ? null
               : Skill.builder()
                      .id(stringOrNull(skillEntity.getId()))
                      .groupName(skillEntity.getGroupName())
                      .title(skillEntity.getTitle())
                      .progress(skillEntity.getProgress())
                      .percent(skillEntity.getPercent())
                      .years(skillEntity.getYears())
                      .since(skillEntity.getSince()
                                        .atZone(ZoneOffset.UTC))
                      .imageUrl(skillEntity.getImageUrl())
                      .active(skillEntity.isActive())
                      .prg(skillEntity.getPrg())
                      .build();
    }

    public static List<Skill> entitiesToPresentation(final List<com.danieleautizi.service.personaldata.model.entity.Skill> skillEntities) {

        return skillEntities == null
               ? Collections.EMPTY_LIST
               : skillEntities.stream()
                              .map(n -> entityToPresentation(n))
                              .collect(Collectors.toList());
    }

}
