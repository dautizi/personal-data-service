package com.danieleautizi.service.personaldata.utility;

import static com.danieleautizi.service.personaldata.utility.DateTimeUtil.localDateTimeOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.objectIdOrNull;
import static com.danieleautizi.service.personaldata.utility.MongoUtils.stringOrNull;

import com.danieleautizi.service.personaldata.model.presentation.Blog;

import lombok.experimental.UtilityClass;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter between {@link Blog} and {@link com.danieleautizi.service.personaldata.model.entity.Blog}.
 */
@UtilityClass
public class BlogConverter {

    public static com.danieleautizi.service.personaldata.model.entity.Blog presentationToEntity(final Blog blog) {

        return blog == null
               ? null
               : com.danieleautizi.service.personaldata.model.entity.Blog.builder()
                                                                         .id(objectIdOrNull(blog.getId()))
                                                                         .title(blog.getTitle())
                                                                         .category(blog.getCategory())
                                                                         .section(blog.getSection())
                                                                         .tag(blog.getTag())

                                                                         .keywords(blog.getKeywords())
                                                                         .cssClass(blog.getCssClass())
                                                                         .image(blog.getImage())
                                                                         .icon(blog.getIcon())
                                                                         .altImage(blog.getAltImage())
                                                                         .articleUrl(blog.getArticleUrl())
                                                                         .staticUrl(blog.getStaticUrl())
                                                                         .description(blog.getDescription())

                                                                         .blogType(blog.getBlogType())
                                                                         .viewType(blog.getViewType())
                                                                         .mediaCssClass(blog.getMediaCssClass())
                                                                         .active(blog.isActive())
                                                                         .prg(blog.getPrg())

                                                                         .datetime(localDateTimeOrNull(blog.getDatetime()))
                                                                         .lastUpdate(localDateTimeOrNull(blog.getLastUpdate()))

                                                                         .build();
    }

    public static Blog entityToPresentation(final com.danieleautizi.service.personaldata.model.entity.Blog blogEntity) {

        return blogEntity == null
               ? null
               : Blog.builder()
                     .id(stringOrNull(blogEntity.getId()))
                     .title(blogEntity.getTitle())
                     .category(blogEntity.getTag())
                     .section(blogEntity.getSection())
                     .tag(blogEntity.getTag())

                     .keywords(blogEntity.getKeywords())
                     .cssClass(blogEntity.getCssClass())
                     .image(blogEntity.getImage())
                     .icon(blogEntity.getIcon())
                     .altImage(blogEntity.getAltImage())
                     .articleUrl(blogEntity.getArticleUrl())
                     .staticUrl(blogEntity.getStaticUrl())
                     .description(blogEntity.getDescription())

                     .blogType(blogEntity.getBlogType())
                     .viewType(blogEntity.getViewType())
                     .mediaCssClass(blogEntity.getMediaCssClass())
                     .active(blogEntity.isActive())
                     .prg(blogEntity.getPrg())

                     .datetime(blogEntity.getDatetime()
                                         .atZone(ZoneOffset.UTC))
                     .lastUpdate(blogEntity.getLastUpdate()
                                           .atZone(ZoneOffset.UTC))
                     .build();
    }

    public static List<Blog> entitiesToPresentation(final List<com.danieleautizi.service.personaldata.model.entity.Blog> blogEntities) {

        return blogEntities == null
               ? Collections.EMPTY_LIST
               : blogEntities.stream()
                             .map(n -> entityToPresentation(n))
                             .collect(Collectors.toList());
    }

}
