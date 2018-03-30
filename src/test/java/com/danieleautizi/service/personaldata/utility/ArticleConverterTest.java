package com.danieleautizi.service.personaldata.utility;

import static org.junit.Assert.assertEquals;

import com.danieleautizi.service.personaldata.model.presentation.Article;
import com.danieleautizi.service.personaldata.model.presentation.Image;

import lombok.val;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ArticleConverterTest {

    private static final ZonedDateTime FAKE_NOW = ZonedDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime FIXED_NOW = DateTimeUtil.fixClockAt(FAKE_NOW);

    private static final ZonedDateTime DATETIME = ZonedDateTime.of(LocalDate.of(2015, 5, 2), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime LAST_UPDATE = DATETIME.plusMonths(6);

    private static final ObjectId image1Id = ObjectId.get();
    private static final Image image1 = Image.builder()
                                             .id(image1Id.toHexString())
                                             .build();
    private static final ObjectId image2Id = ObjectId.get();
    private static final Image image2 = Image.builder()
                                             .id(image2Id.toHexString())
                                             .build();
    private static final List<Image> IMAGES = Arrays.asList(image1, image2);
    private static final List<ObjectId> IMAGE_IDS = Arrays.asList(image1Id, image2Id);

    private static final String DETAIL_PATH = "/unique/path/adventure.html";
    private static final String URL = "http://test.com/adventure/article-1.html";
    private static final String HEADER = "Category 1";
    private static final String IMAGE = "http://test.com/images/picture-1.jpg";
    private static final String THUMB = "http://test.com/images/picture-1-thumb.jpg";
    private static final String TITLE = "Title";
    private static final String SUMMARY = "Summary";
    private static final String BODY = "Body";
    private static final String AUTHOR = "Author Autizi";

    private static final boolean ACTIVE = true;
    private static final int PRG = 1;

    @Test
    public void convertPresentationToEntity() {

        val expectedEntity = com.danieleautizi.service.personaldata.model.entity.Article.builder()
                                                                                        .detailPath(DETAIL_PATH)
                                                                                        .url(URL)
                                                                                        .header(HEADER)
                                                                                        .image(IMAGE)
                                                                                        .thumb(THUMB)
                                                                                        .title(TITLE)
                                                                                        .summary(SUMMARY)
                                                                                        .body(BODY)
                                                                                        .author(AUTHOR)
                                                                                        .prg(PRG)
                                                                                        .active(ACTIVE)
                                                                                        .datetime(DATETIME.toLocalDateTime())
                                                                                        .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                        .images(IMAGE_IDS)
                                                                                        .build();

        val actualArticle = Article.builder()
                                   .detailPath(DETAIL_PATH)
                                   .url(URL)
                                   .header(HEADER)
                                   .image(IMAGE)
                                   .thumb(THUMB)
                                   .title(TITLE)
                                   .summary(SUMMARY)
                                   .body(BODY)
                                   .author(AUTHOR)
                                   .prg(PRG)
                                   .active(ACTIVE)
                                   .datetime(DATETIME)
                                   .lastUpdate(LAST_UPDATE)
                                   .images(IMAGES)
                                   .build();

        assertEquals(expectedEntity, ArticleConverter.presentationToEntity(actualArticle));
    }

    @Test
    public void convertEntityToPresentation() {

        val expectedArticle = Article.builder()
                                     .detailPath(DETAIL_PATH)
                                     .url(URL)
                                     .header(HEADER)
                                     .image(IMAGE)
                                     .thumb(THUMB)
                                     .title(TITLE)
                                     .summary(SUMMARY)
                                     .body(BODY)
                                     .author(AUTHOR)
                                     .prg(PRG)
                                     .active(ACTIVE)
                                     .datetime(DATETIME)
                                     .lastUpdate(LAST_UPDATE)
                                     .images(IMAGES)
                                     .build();

        val actualEntity = com.danieleautizi.service.personaldata.model.entity.Article.builder()
                                                                                        .detailPath(DETAIL_PATH)
                                                                                        .url(URL)
                                                                                        .header(HEADER)
                                                                                        .image(IMAGE)
                                                                                        .thumb(THUMB)
                                                                                        .title(TITLE)
                                                                                        .summary(SUMMARY)
                                                                                        .body(BODY)
                                                                                        .author(AUTHOR)
                                                                                        .prg(PRG)
                                                                                        .active(ACTIVE)
                                                                                        .datetime(DATETIME.toLocalDateTime())
                                                                                        .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                        .images(IMAGE_IDS)
                                                                                        .build();

        assertEquals(expectedArticle, ArticleConverter.entityToPresentation(actualEntity));
    }
}
