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
public class CurriculumVitae {

    @Id
    private ObjectId id;

    private String title;
    private String path;
    private String filename;
    private String description;

    @Indexed
    private boolean active;
    @Indexed
    private int prg;
}
