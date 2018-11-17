package com.danieleautizi.service.personaldata.utility;

import static org.junit.Assert.assertEquals;

import com.danieleautizi.service.personaldata.model.presentation.Image;

import lombok.val;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ImageConverterTest {

    private static final String TITLE = "Picture 1";
    private static final String URL = "http://test.com/pictures/picture-1.png";
    private static final String TYPE = "Picture";
    private static final boolean ACTIVE = true;
    private static final int PRG = 1;

    @Test
    public void convertPresentationToEntity() {

        val expectedEntity = com.danieleautizi.service.personaldata.model.entity.Image.builder()
                                                                                      .title(TITLE)
                                                                                      .url(URL)
                                                                                      .type(TYPE)
                                                                                      .active(ACTIVE)
                                                                                      .prg(PRG)
                                                                                      .build();

        val actualImage = Image.builder()
                               .title(TITLE)
                               .url(URL)
                               .type(TYPE)
                               .active(ACTIVE)
                               .prg(PRG)
                               .build();

        assertEquals(expectedEntity, ImageConverter.presentationToEntity(actualImage));
    }

    @Test
    public void convertPresentationToEntity_includingId() {

        val id = ObjectId.get();
        val expectedEntity = com.danieleautizi.service.personaldata.model.entity.Image.builder()
                                                                                      .id(id)
                                                                                      .title(TITLE)
                                                                                      .url(URL)
                                                                                      .type(TYPE)
                                                                                      .active(ACTIVE)
                                                                                      .prg(PRG)
                                                                                      .build();

        val actualImage = Image.builder()
                               .id(id.toHexString())
                               .title(TITLE)
                               .url(URL)
                               .type(TYPE)
                               .active(ACTIVE)
                               .prg(PRG)
                               .build();

        assertEquals(expectedEntity, ImageConverter.presentationToEntity(actualImage));
    }

    @Test
    public void convertEntityToPresentation() {

        val expectedImage = Image.builder()
                                 .title(TITLE)
                                 .url(URL)
                                 .type(TYPE)
                                 .active(ACTIVE)
                                 .prg(PRG)
                                 .build();

        val actualEntity = com.danieleautizi.service.personaldata.model.entity.Image.builder()
                                                                                    .title(TITLE)
                                                                                    .url(URL)
                                                                                    .type(TYPE)
                                                                                    .active(ACTIVE)
                                                                                    .prg(PRG)
                                                                                    .build();

        assertEquals(expectedImage, ImageConverter.entityToPresentation(actualEntity));
    }

    @Test
    public void convertEntityToPresentation_includingId() {

        val id = ObjectId.get();
        val expectedImage = Image.builder()
                                 .id(id.toHexString())
                                 .title(TITLE)
                                 .url(URL)
                                 .type(TYPE)
                                 .active(ACTIVE)
                                 .prg(PRG)
                                 .build();

        val actualEntity = com.danieleautizi.service.personaldata.model.entity.Image.builder()
                                                                                    .id(id)
                                                                                    .title(TITLE)
                                                                                    .url(URL)
                                                                                    .type(TYPE)
                                                                                    .active(ACTIVE)
                                                                                    .prg(PRG)
                                                                                    .build();

        assertEquals(expectedImage, ImageConverter.entityToPresentation(actualEntity));
    }
}
