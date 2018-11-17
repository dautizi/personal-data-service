package com.danieleautizi.service.personaldata.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    private ObjectId id;

    private String title;
    private String url;
    private String type;

    @Indexed
    private boolean active;
    @Indexed
    private int prg;
}
