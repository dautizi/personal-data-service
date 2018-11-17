package com.danieleautizi.service.personaldata.utility;

import static org.junit.Assert.assertEquals;

import com.danieleautizi.service.personaldata.model.presentation.Blog;

import lombok.val;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RunWith(MockitoJUnitRunner.class)
public class BlogConverterTest {

    private static final ZonedDateTime FAKE_NOW = ZonedDateTime.of(LocalDate.of(2018, 3, 30), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime FIXED_NOW = DateTimeUtil.fixClockAt(FAKE_NOW);

    private static final ZonedDateTime DATETIME = ZonedDateTime.of(LocalDate.of(2015, 5, 2), LocalTime.MIN, ZoneOffset.UTC);
    private static final ZonedDateTime LAST_UPDATE = DATETIME.plusMonths(6);

    private static final String TITLE = "Title";
    private static final String CATEGORY = "Category 1";
    private static final String SECTION = "Section";
    private static final String TAG = "Tag1";
    private static final String KEYWORDS = "Key1, Key2, Key3";
    private static final String CSS_CLASS = "style-blog";
    private static final String IMAGE = "http://test.com/images/picture-1.jpg";
    private static final String ICON = "http://test.com/images/picture-1-icon.jpg";
    private static final String ALT_IMAGE = "Alt image";
    private static final String URL = "http://test.com/adventure/blog-1.html";
    private static final String DESCRIPTION = "Description about blog.";
    private static final String TYPE = "Type";
    private static final String STATIC_URL = "http://test.com/static/blog/blog-1.html";
    private static final String VIEW_TYPE = "view";
    private static final String MEDIA_CSS_CLASS = "media-css-style";

    private static final boolean ACTIVE = true;
    private static final int PRG = 1;

    @Test
    public void convertPresentationToEntity() {

        val expectedEntity = com.danieleautizi.service.personaldata.model.entity.Blog.builder()
                                                                                     .title(TITLE)
                                                                                     .category(CATEGORY)
                                                                                     .section(SECTION)
                                                                                     .tag(TAG)
                                                                                     .keywords(KEYWORDS)
                                                                                     .cssClass(CSS_CLASS)
                                                                                     .image(IMAGE)
                                                                                     .icon(ICON)
                                                                                     .altImage(ALT_IMAGE)
                                                                                     .articleUrl(URL)
                                                                                     .description(DESCRIPTION)
                                                                                     .staticUrl(STATIC_URL)
                                                                                     .blogType(TYPE)
                                                                                     .viewType(VIEW_TYPE)
                                                                                     .mediaCssClass(MEDIA_CSS_CLASS)
                                                                                     .prg(PRG)
                                                                                     .active(ACTIVE)
                                                                                     .datetime(DATETIME.toLocalDateTime())
                                                                                     .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                     .build();

        val actualBlog = Blog.builder()
                             .title(TITLE)
                             .category(CATEGORY)
                             .section(SECTION)
                             .tag(TAG)
                             .keywords(KEYWORDS)
                             .cssClass(CSS_CLASS)
                             .image(IMAGE)
                             .icon(ICON)
                             .altImage(ALT_IMAGE)
                             .articleUrl(URL)
                             .description(DESCRIPTION)
                             .staticUrl(STATIC_URL)
                             .description(DESCRIPTION)
                             .blogType(TYPE)
                             .viewType(VIEW_TYPE)
                             .mediaCssClass(MEDIA_CSS_CLASS)
                             .prg(PRG)
                             .active(ACTIVE)
                             .datetime(DATETIME)
                             .lastUpdate(LAST_UPDATE)
                             .build();

        assertEquals(expectedEntity, BlogConverter.presentationToEntity(actualBlog));
    }

    @Test
    public void convertEntityToPresentation() {

        val expectedBlog = Blog.builder()
                               .title(TITLE)
                               .category(CATEGORY)
                               .section(SECTION)
                               .tag(TAG)
                               .keywords(KEYWORDS)
                               .cssClass(CSS_CLASS)
                               .image(IMAGE)
                               .icon(ICON)
                               .altImage(ALT_IMAGE)
                               .articleUrl(URL)
                               .description(DESCRIPTION)
                               .staticUrl(STATIC_URL)
                               .description(DESCRIPTION)
                               .blogType(TYPE)
                               .viewType(VIEW_TYPE)
                               .mediaCssClass(MEDIA_CSS_CLASS)
                               .prg(PRG)
                               .active(ACTIVE)
                               .datetime(DATETIME)
                               .lastUpdate(LAST_UPDATE)
                               .build();

        val actualEntity = com.danieleautizi.service.personaldata.model.entity.Blog.builder()
                                                                                   .title(TITLE)
                                                                                   .category(CATEGORY)
                                                                                   .section(SECTION)
                                                                                   .tag(TAG)
                                                                                   .keywords(KEYWORDS)
                                                                                   .cssClass(CSS_CLASS)
                                                                                   .image(IMAGE)
                                                                                   .icon(ICON)
                                                                                   .altImage(ALT_IMAGE)
                                                                                   .articleUrl(URL)
                                                                                   .description(DESCRIPTION)
                                                                                   .staticUrl(STATIC_URL)
                                                                                   .description(DESCRIPTION)
                                                                                   .blogType(TYPE)
                                                                                   .viewType(VIEW_TYPE)
                                                                                   .mediaCssClass(MEDIA_CSS_CLASS)
                                                                                   .prg(PRG)
                                                                                   .active(ACTIVE)
                                                                                   .datetime(DATETIME.toLocalDateTime())
                                                                                   .lastUpdate(LAST_UPDATE.toLocalDateTime())
                                                                                   .build();

        assertEquals(expectedBlog, BlogConverter.entityToPresentation(actualEntity));
    }
}
