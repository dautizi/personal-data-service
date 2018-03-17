package com.danieleautizi.service.personaldata.utility;

import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.localDateTimeOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.objectIdOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringOrNull;

import com.danieleautizi.service.personaldata.model.presentation.Article;
import com.danieleautizi.service.personaldata.model.presentation.Image;

import lombok.experimental.UtilityClass;

import org.bson.types.ObjectId;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter between {@link Article} and {@link com.danieleautizi.service.personaldata.model.entity.Article}.
 */
@UtilityClass
public class ArticleConverter {

    public static com.danieleautizi.service.personaldata.model.entity.Article presentationToEntity(final Article article) {

        return article == null
               ? null
               : com.danieleautizi.service.personaldata.model.entity.Article.builder()
                                                                            .id(objectIdOrNull(article.getId()))
                                                                            .detailPath(article.getDetailPath())
                                                                            .url(article.getUrl())
                                                                            .header(article.getHeader())
                                                                            .image(article.getImage())
                                                                            .thumb(article.getThumb())

                                                                            .title(article.getTitle())
                                                                            .summary(article.getSummary())
                                                                            .body(article.getBody())
                                                                            .author(article.getAuthor())

                                                                            .active(article.isActive())
                                                                            .prg(article.getPrg())

                                                                            .datetime(localDateTimeOrNull(article.getDatetime()))
                                                                            .lastUpdate(localDateTimeOrNull(article.getLastUpdate()))

                                                                            .images(getImageIds(article.getImages()))
                                                                            .build();
    }

    public static Article entityToPresentation(final com.danieleautizi.service.personaldata.model.entity.Article articleEntity) {

        return articleEntity == null
               ? null
               : Article.builder()
                        .id(stringOrNull(articleEntity.getId()))
                        .detailPath(articleEntity.getDetailPath())
                        .url(articleEntity.getUrl())
                        .header(articleEntity.getHeader())
                        .image(articleEntity.getImage())
                        .thumb(articleEntity.getThumb())

                        .title(articleEntity.getTitle())
                        .summary(articleEntity.getSummary())
                        .body(articleEntity.getBody())
                        .author(articleEntity.getAuthor())

                        .active(articleEntity.isActive())
                        .prg(articleEntity.getPrg())

                        .datetime(articleEntity.getDatetime()
                                               .atZone(ZoneOffset.UTC))
                        .lastUpdate(articleEntity.getLastUpdate()
                                                 .atZone(ZoneOffset.UTC))
                        .images(getImageList(articleEntity.getImages()))
                        .build();
    }

    public static List<Article> entitiesToPresentation(final List<com.danieleautizi.service.personaldata.model.entity.Article> articleEntities) {

        return articleEntities == null
               ? Collections.EMPTY_LIST
               : articleEntities.stream()
                                .map(n -> entityToPresentation(n))
                                .collect(Collectors.toList());
    }

    private static List<ObjectId> getImageIds(final List<Image> images) {

        return images == null
               ? Collections.emptyList()
               : images.stream()
                       .map(image -> objectIdOrNull(image.getId()))
                       .collect(Collectors.toList());
    }

    private static List<Image> getImageList(final List<ObjectId> imageIds) {

        return imageIds == null
               ? Collections.emptyList()
               : imageIds.stream()
                         .map(imageId -> Image.builder()
                                              .id(stringOrNull(imageId))
                                              .build())
                         .collect(Collectors.toList());
    }
}
