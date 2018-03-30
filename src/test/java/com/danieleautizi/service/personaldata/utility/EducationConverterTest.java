package com.danieleautizi.service.personaldata.utility;

import static org.junit.Assert.assertEquals;

import com.danieleautizi.service.personaldata.model.presentation.Education;

import lombok.val;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RunWith(MockitoJUnitRunner.class)
public class EducationConverterTest {

    private static final ZonedDateTime FAKE_NOW = ZonedDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime FIXED_NOW = DateTimeUtil.fixClockAt(FAKE_NOW);

    private static final ZonedDateTime DATETIME = ZonedDateTime.of(LocalDate.of(2015, 5, 2), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime LAST_UPDATE = DATETIME.plusMonths(6);

    private static final String SCHOOL = "La Sapienza University";
    private static final String TITLE = "Software Engineering";
    private static final String DESCRIPTION = "Description for a specific education experience.";
    private static final String SCHOOL_IMAGE = "http://test.com/images/school/university-1.jpg";
    private static final String SCHOOL_THUMB = "http://test.com/images/school/university-1-thumb.jpg";
    private static final String START_YEAR = "2001";
    private static final String END_YEAR = "2006";
    private static final boolean ACTIVE = true;
    private static final int PRG = 1;

    @Test
    public void convertPresentationToEntity() {

        val expectedEntity = com.danieleautizi.service.personaldata.model.entity.Education.builder()
                                                                                          .school(SCHOOL)
                                                                                          .title(TITLE)
                                                                                          .description(DESCRIPTION)
                                                                                          .schoolImage(SCHOOL_IMAGE)
                                                                                          .schoolThumb(SCHOOL_THUMB)
                                                                                          .startYear(START_YEAR)
                                                                                          .endYear(END_YEAR)
                                                                                          .prg(PRG)
                                                                                          .active(ACTIVE)
                                                                                          .datetime(DATETIME.toLocalDateTime())
                                                                                          .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                          .build();

        val actualEducation = Education.builder()
                                       .school(SCHOOL)
                                       .title(TITLE)
                                       .description(DESCRIPTION)
                                       .schoolImage(SCHOOL_IMAGE)
                                       .schoolThumb(SCHOOL_THUMB)
                                       .startYear(START_YEAR)
                                       .endYear(END_YEAR)
                                       .prg(PRG)
                                       .active(ACTIVE)
                                       .datetime(DATETIME)
                                       .lastUpdate(LAST_UPDATE)
                                       .build();

        assertEquals(expectedEntity, EducationConverter.presentationToEntity(actualEducation));
    }

    @Test
    public void convertEntityToPresentation() {

        val expectedEducation = Education.builder()
                                         .school(SCHOOL)
                                         .title(TITLE)
                                         .description(DESCRIPTION)
                                         .schoolImage(SCHOOL_IMAGE)
                                         .schoolThumb(SCHOOL_THUMB)
                                         .startYear(START_YEAR)
                                         .endYear(END_YEAR)
                                         .prg(PRG)
                                         .active(ACTIVE)
                                         .datetime(DATETIME)
                                         .lastUpdate(LAST_UPDATE)
                                         .build();

        val actualEntity = com.danieleautizi.service.personaldata.model.entity.Education.builder()
                                                                                        .school(SCHOOL)
                                                                                        .title(TITLE)
                                                                                        .description(DESCRIPTION)
                                                                                        .schoolImage(SCHOOL_IMAGE)
                                                                                        .schoolThumb(SCHOOL_THUMB)
                                                                                        .startYear(START_YEAR)
                                                                                        .endYear(END_YEAR)
                                                                                        .prg(PRG)
                                                                                        .active(ACTIVE)
                                                                                        .datetime(DATETIME.toLocalDateTime())
                                                                                        .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                        .build();

        assertEquals(expectedEducation, EducationConverter.entityToPresentation(actualEntity));
    }
}
