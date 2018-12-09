package com.danieleautizi.service.personaldata.utility;

import static org.junit.Assert.assertEquals;

import com.danieleautizi.service.personaldata.model.presentation.CurriculumVitae;

import lombok.val;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CurriculumVitaeConverterTest {

    private static final ObjectId ID = ObjectId.get();
    private static final String TITLE = "Software Engineering";
    private static final String DESCRIPTION = "Description for a specific education experience.";
    private static final String PATH = "/cv/en-cv-1.pdf";
    private static final String FILENAME = "en-cv-1.pdf";
    private static final boolean ACTIVE = true;
    private static final int PRG = 1;

    @Test
    public void convertPresentationToEntity() {

        val expectedEntity = com.danieleautizi.service.personaldata.model.entity.CurriculumVitae.builder()
                                                                                                .title(TITLE)
                                                                                                .description(DESCRIPTION)
                                                                                                .path(PATH)
                                                                                                .filename(FILENAME)
                                                                                                .prg(PRG)
                                                                                                .active(ACTIVE)
                                                                                                .build();

        val actualCV = CurriculumVitae.builder()
                                      .title(TITLE)
                                      .description(DESCRIPTION)
                                      .path(PATH)
                                      .filename(FILENAME)
                                      .prg(PRG)
                                      .active(ACTIVE)
                                      .build();

        assertEquals(expectedEntity, CurriculumVitaeConverter.presentationToEntity(actualCV));
    }

    @Test
    public void convertEntityToPresentation() {

        val expectedCV = CurriculumVitae.builder()
                                        .id(ID.toHexString())
                                        .title(TITLE)
                                        .description(DESCRIPTION)
                                        .path(PATH)
                                        .filename(FILENAME)
                                        .prg(PRG)
                                        .active(ACTIVE)
                                        .build();

        val actualEntity = com.danieleautizi.service.personaldata.model.entity.CurriculumVitae.builder()
                                                                                              .id(ID)
                                                                                              .title(TITLE)
                                                                                              .description(DESCRIPTION)
                                                                                              .path(PATH)
                                                                                              .filename(FILENAME)
                                                                                              .prg(PRG)
                                                                                              .active(ACTIVE)
                                                                                              .build();

        assertEquals(expectedCV, CurriculumVitaeConverter.entityToPresentation(actualEntity));
    }
}
