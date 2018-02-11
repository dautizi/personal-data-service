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
import java.util.List;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adventure {

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
    private String description;

    @Indexed
    private String adventureType;

    private String staticUrl;
    private String viewType;
    private String mediaCssClass;

    @Indexed
    private boolean active;
    @Indexed
    private int prg;

    @Indexed
    private LocalDateTime datetime;
    @Indexed
    private LocalDateTime lastUpdate;

    private List<AdventureMedia> adventureMedia;
}
