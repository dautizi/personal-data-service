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
public class Article {

    @Id
    private ObjectId id;

    private String detailPath;
    private String url;
    private String header;
    private String image;
    private String thumb;
    private String title;
    private String summary;
    private String body;
    private String author;

    @Indexed
    private boolean active;
    @Indexed
    private int prg;

    @Indexed
    private LocalDateTime datetime;
    @Indexed
    private LocalDateTime lastUpdate;

    private List<ObjectId> images;
}
