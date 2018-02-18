package com.danieleautizi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    @Id
    private ObjectId id;

    private String title;
    private String category;
    private String section;
    private String tag;

    private String keywords;

    private String cssClass;
    private String image;
    private String icon;
    private String altImage;
    private String articleUrl;
    private String staticUrl;
    private String description;

    private String blogType;
    private String viewType;
    private String mediaCssClass;

    @Indexed
    private LocalDateTime datetime;
    @Indexed
    private LocalDateTime lastUpdate;

    @Indexed
    private boolean active;
    @Indexed
    private int prg;
}
