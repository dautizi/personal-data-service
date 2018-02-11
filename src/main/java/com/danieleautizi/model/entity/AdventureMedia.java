package com.danieleautizi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdventureMedia {

    @Indexed
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
}
