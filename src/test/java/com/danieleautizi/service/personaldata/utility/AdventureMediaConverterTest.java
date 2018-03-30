package com.danieleautizi.service.personaldata.utility;

import static org.junit.Assert.assertEquals;

import com.danieleautizi.service.personaldata.model.presentation.AdventureMedia;

import lombok.val;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RunWith(MockitoJUnitRunner.class)
public class AdventureMediaConverterTest {

    private static final ZonedDateTime FAKE_NOW = ZonedDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime FIXED_NOW = DateTimeUtil.fixClockAt(FAKE_NOW);

    private static final ZonedDateTime DATETIME = ZonedDateTime.of(LocalDate.of(2015, 5, 2), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime LAST_UPDATE = DATETIME.plusMonths(6);

    private static final String MEDIA_TYPE = "image";
    private static final String MEDIA_PATH = "/pages/adventure/location/picture-1.jpg";
    private static final String MEDIA_URL = "http://test.com/images/pages/adventure/location/picture-1.jpg";
    private static final String TITLE = "Media title";
    private static final String ALT = "Media alt";
    private static final String CSS_CLASS = "style-media-adventure";
    private static final boolean ACTIVE = true;
    private static final int PRG = 1;

    @Test
    public void convertPresentationToEntity() {

        val expectedEntity = com.danieleautizi.service.personaldata.model.entity.AdventureMedia.builder()
                                                                                               .mediaType(MEDIA_TYPE)
                                                                                               .mediaPath(MEDIA_PATH)
                                                                                               .mediaUrl(MEDIA_URL)
                                                                                               .title(TITLE)
                                                                                               .alt(ALT)
                                                                                               .cssClass(CSS_CLASS)
                                                                                               .prg(PRG)
                                                                                               .active(ACTIVE)
                                                                                               .datetime(DATETIME.toLocalDateTime())
                                                                                               .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                               .build();

        val actualAdventureMedia = AdventureMedia.builder()
                                                 .mediaType(MEDIA_TYPE)
                                                 .mediaPath(MEDIA_PATH)
                                                 .mediaUrl(MEDIA_URL)
                                                 .title(TITLE)
                                                 .alt(ALT)
                                                 .cssClass(CSS_CLASS)
                                                 .prg(PRG)
                                                 .active(ACTIVE)
                                                 .datetime(DATETIME)
                                                 .lastUpdate(LAST_UPDATE)
                                                 .build();

        assertEquals(expectedEntity, AdventureMediaConverter.presentationToEntity(actualAdventureMedia));
    }

    @Test
    public void convertEntityToPresentation() {

        val expectedAdventureMedia = AdventureMedia.builder()
                                                   .mediaType(MEDIA_TYPE)
                                                   .mediaPath(MEDIA_PATH)
                                                   .mediaUrl(MEDIA_URL)
                                                   .title(TITLE)
                                                   .alt(ALT)
                                                   .cssClass(CSS_CLASS)
                                                   .prg(PRG)
                                                   .active(ACTIVE)
                                                   .datetime(DATETIME)
                                                   .lastUpdate(LAST_UPDATE)
                                                   .build();

        val actualEntity = com.danieleautizi.service.personaldata.model.entity.AdventureMedia.builder()
                                                                                             .mediaType(MEDIA_TYPE)
                                                                                             .mediaPath(MEDIA_PATH)
                                                                                             .mediaUrl(MEDIA_URL)
                                                                                             .title(TITLE)
                                                                                             .alt(ALT)
                                                                                             .cssClass(CSS_CLASS)
                                                                                             .prg(PRG)
                                                                                             .active(ACTIVE)
                                                                                             .datetime(DATETIME.toLocalDateTime())
                                                                                             .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                             .build();

        assertEquals(expectedAdventureMedia, AdventureMediaConverter.entityToPresentation(actualEntity));
    }
}
