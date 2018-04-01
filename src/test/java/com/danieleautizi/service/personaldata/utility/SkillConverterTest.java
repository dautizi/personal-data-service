package com.danieleautizi.service.personaldata.utility;

import static org.junit.Assert.assertEquals;

import com.danieleautizi.service.personaldata.model.presentation.Skill;

import lombok.val;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RunWith(MockitoJUnitRunner.class)
public class SkillConverterTest {

    private static final ZonedDateTime FAKE_NOW = ZonedDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime FIXED_NOW = DateTimeUtil.fixClockAt(FAKE_NOW);
    private static final ZonedDateTime SINCE = ZonedDateTime.of(LocalDate.of(2013, 5, 2), LocalTime.MIN, ZoneOffset.UTC);

    private static final ZonedDateTime DATETIME = ZonedDateTime.of(LocalDate.of(2015, 5, 2), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime LAST_UPDATE = DATETIME.plusMonths(6);

    private static final String GROUP_NAME = "Languages";
    private static final String TITLE = "Java";
    private static final Integer PROGRESS = 75;
    private static final Integer PERCENTAGE = 75;
    private static final Integer YEARS = 4;
    private static final String IMAGE_URL = "http://test.com/skill/picture-skill-1.png";
    private static final boolean ACTIVE = true;
    private static final int PRG = 1;

    @Test
    public void convertPresentationToEntity() {

        val expectedEntity = com.danieleautizi.service.personaldata.model.entity.Skill.builder()
                                                                                      .groupName(GROUP_NAME)
                                                                                      .title(TITLE)
                                                                                      .progress(PROGRESS)
                                                                                      .percentage(PERCENTAGE)
                                                                                      .since(SINCE.toLocalDateTime())
                                                                                      .imageUrl(IMAGE_URL)
                                                                                      .active(ACTIVE)
                                                                                      .prg(PRG)
                                                                                      .datetime(DATETIME.toLocalDateTime())
                                                                                      .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                      .build();

        val actualSkill = Skill.builder()
                               .groupName(GROUP_NAME)
                               .title(TITLE)
                               .progress(PROGRESS)
                               .percentage(PERCENTAGE)
                               .years(YEARS)
                               .since(SINCE)
                               .imageUrl(IMAGE_URL)
                               .active(ACTIVE)
                               .prg(PRG)
                               .datetime(DATETIME)
                               .lastUpdate(LAST_UPDATE)
                               .build();

        assertEquals(expectedEntity, SkillConverter.presentationToEntity(actualSkill));
    }

    @Test
    public void convertEntityToPresentation() {

        val expectedSkill = Skill.builder()
                                 .groupName(GROUP_NAME)
                                 .title(TITLE)
                                 .progress(PROGRESS)
                                 .percentage(PERCENTAGE)
                                 .years(YEARS)
                                 .since(SINCE)
                                 .imageUrl(IMAGE_URL)
                                 .active(ACTIVE)
                                 .prg(PRG)
                                 .datetime(DATETIME)
                                 .lastUpdate(LAST_UPDATE)
                                 .build();

        val actualEntity = com.danieleautizi.service.personaldata.model.entity.Skill.builder()
                                                                                    .groupName(GROUP_NAME)
                                                                                    .title(TITLE)
                                                                                    .progress(PROGRESS)
                                                                                    .percentage(PERCENTAGE)
                                                                                    .since(SINCE.toLocalDateTime())
                                                                                    .imageUrl(IMAGE_URL)
                                                                                    .active(ACTIVE)
                                                                                    .prg(PRG)
                                                                                    .datetime(DATETIME.toLocalDateTime())
                                                                                    .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                    .build();

        assertEquals(expectedSkill, SkillConverter.entityToPresentation(actualEntity));
    }
}
