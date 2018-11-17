package com.danieleautizi.service.personaldata.utility;

import static org.junit.Assert.assertEquals;

import com.danieleautizi.service.personaldata.model.presentation.WorkExperience;

import lombok.val;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RunWith(MockitoJUnitRunner.class)
public class WorkExperienceConverterTest {

    private static final ZonedDateTime FAKE_NOW = ZonedDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime FIXED_NOW = DateTimeUtil.fixClockAt(FAKE_NOW);

    private static final ZonedDateTime DATETIME = ZonedDateTime.of(LocalDate.of(2015, 5, 2), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime LAST_UPDATE = DATETIME.plusMonths(6);

    private static final String COMPANY = "L'Espresso Group";
    private static final String ROLE = "Fullstack Developer";
    private static final String DESCRIPTION = "Description for a specific work experience.";
    private static final String PERIOD = "2013 - 2015";
    private static final String COMPANY_IMAGE = "http://test.com/images/company/company-1.jpg";
    private static final String COMPANY_THUMB = "http://test.com/images/company/company-1-thumb.jpg";
    private static final String START_YEAR = "2013";
    private static final String END_YEAR = "2015";
    private static final boolean ACTIVE = true;
    private static final int PRG = 1;

    @Test
    public void convertPresentationToEntity() {

        val expectedEntity = com.danieleautizi.service.personaldata.model.entity.WorkExperience.builder()
                                                                                               .company(COMPANY)
                                                                                               .role(ROLE)
                                                                                               .description(DESCRIPTION)
                                                                                               .period(PERIOD)
                                                                                               .companyImage(COMPANY_IMAGE)
                                                                                               .companyThumb(COMPANY_THUMB)
                                                                                               .startYear(START_YEAR)
                                                                                               .endYear(END_YEAR)
                                                                                               .prg(PRG)
                                                                                               .active(ACTIVE)
                                                                                               .datetime(DATETIME.toLocalDateTime())
                                                                                               .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                               .build();

        val actualWorkExperience = WorkExperience.builder()
                                                 .company(COMPANY)
                                                 .role(ROLE)
                                                 .description(DESCRIPTION)
                                                 .period(PERIOD)
                                                 .companyImage(COMPANY_IMAGE)
                                                 .companyThumb(COMPANY_THUMB)
                                                 .startYear(START_YEAR)
                                                 .endYear(END_YEAR)
                                                 .prg(PRG)
                                                 .active(ACTIVE)
                                                 .datetime(DATETIME)
                                                 .lastUpdate(LAST_UPDATE)
                                                 .build();

        assertEquals(expectedEntity, WorkExperienceConverter.presentationToEntity(actualWorkExperience));
    }

    @Test
    public void convertEntityToPresentation() {

        val expectedWorkExperience = WorkExperience.builder()
                                                   .company(COMPANY)
                                                   .role(ROLE)
                                                   .description(DESCRIPTION)
                                                   .period(PERIOD)
                                                   .companyImage(COMPANY_IMAGE)
                                                   .companyThumb(COMPANY_THUMB)
                                                   .startYear(START_YEAR)
                                                   .endYear(END_YEAR)
                                                   .prg(PRG)
                                                   .active(ACTIVE)
                                                   .datetime(DATETIME)
                                                   .lastUpdate(LAST_UPDATE)
                                                   .build();

        val actualEntity = com.danieleautizi.service.personaldata.model.entity.WorkExperience.builder()
                                                                                             .company(COMPANY)
                                                                                             .role(ROLE)
                                                                                             .description(DESCRIPTION)
                                                                                             .period(PERIOD)
                                                                                             .companyImage(COMPANY_IMAGE)
                                                                                             .companyThumb(COMPANY_THUMB)
                                                                                             .startYear(START_YEAR)
                                                                                             .endYear(END_YEAR)
                                                                                             .prg(PRG)
                                                                                             .active(ACTIVE)
                                                                                             .datetime(DATETIME.toLocalDateTime())
                                                                                             .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                             .build();

        assertEquals(expectedWorkExperience, WorkExperienceConverter.entityToPresentation(actualEntity));
    }
}
