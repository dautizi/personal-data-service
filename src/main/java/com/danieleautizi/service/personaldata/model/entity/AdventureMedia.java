package com.danieleautizi.service.personaldata.model.entity;

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
public class AdventureMedia {

    @Id
    private ObjectId id;

    @Indexed
    private String mediaType;

    private String mediaPath;
    private String mediaUrl;
    private String title;
    private String alt;
    private String cssClass;

    private int prg;

    @Indexed
    private boolean active;

    @Indexed
    private LocalDateTime datetime;
    @Indexed
    private LocalDateTime lastUpdate;
}
