package com.danieleautizi.utility;

import static com.danieleautizi.utility.MongoUtils.objectIdOrNull;
import static com.danieleautizi.utility.MongoUtils.stringOrNull;

import com.danieleautizi.model.presentation.Image;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter between {@link Image} and {@link com.danieleautizi.model.entity.Image}.
 */
@UtilityClass
public class ImageConverter {

    public static com.danieleautizi.model.entity.Image presentationToEntity(final Image image) {

        return image == null
               ? null
               : com.danieleautizi.model.entity.Image.builder()
                                                     .id(objectIdOrNull(image.getId()))
                                                     .url(image.getUrl())
                                                     .type(image.getType())
                                                     .active(image.isActive())
                                                     .build();
    }

    public static Image entityToPresentation(final com.danieleautizi.model.entity.Image imageEntity) {

        return imageEntity == null
               ? null
               : Image.builder()
                      .id(stringOrNull(imageEntity.getId()))
                      .url(imageEntity.getUrl())
                      .type(imageEntity.getType())
                      .active(imageEntity.isActive())
                      .build();
    }

    public static List<Image> entitiesToPresentation(final List<com.danieleautizi.model.entity.Image> imageEntities) {

        return imageEntities == null
               ? Collections.EMPTY_LIST
               : imageEntities.stream()
                              .map(n -> entityToPresentation(n))
                              .collect(Collectors.toList());
    }

}
