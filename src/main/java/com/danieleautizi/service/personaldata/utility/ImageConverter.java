package com.danieleautizi.service.personaldata.utility;

import static com.danieleautizi.service.personaldata.utility.MongoUtils.objectIdOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringOrNull;

import com.danieleautizi.service.personaldata.model.presentation.Image;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter between {@link Image} and {@link com.danieleautizi.service.personaldata.model.entity.Image}.
 */
@UtilityClass
public class ImageConverter {

    public static com.danieleautizi.service.personaldata.model.entity.Image presentationToEntity(final Image image) {

        return image == null
               ? null
               : com.danieleautizi.service.personaldata.model.entity.Image.builder()
                                                                          .id(objectIdOrNull(image.getId()))
                                                                          .title(image.getTitle())
                                                                          .url(image.getUrl())
                                                                          .type(image.getType())
                                                                          .active(image.isActive())
                                                                          .prg(image.getPrg())
                                                                          .build();
    }

    public static Image entityToPresentation(final com.danieleautizi.service.personaldata.model.entity.Image imageEntity) {

        return imageEntity == null
               ? null
               : Image.builder()
                      .id(stringOrNull(imageEntity.getId()))
                      .title(imageEntity.getTitle())
                      .url(imageEntity.getUrl())
                      .type(imageEntity.getType())
                      .active(imageEntity.isActive())
                      .prg(imageEntity.getPrg())
                      .build();
    }

    public static List<Image> entitiesToPresentation(final List<com.danieleautizi.service.personaldata.model.entity.Image> imageEntities) {

        return imageEntities == null
               ? Collections.EMPTY_LIST
               : imageEntities.stream()
                              .map(n -> entityToPresentation(n))
                              .collect(Collectors.toList());
    }

}
