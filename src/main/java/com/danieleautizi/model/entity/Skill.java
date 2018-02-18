package com.danieleautizi.model.entity;

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
public class Skill {

    @Id
    private ObjectId id;

    @Indexed
    private String groupName;
    private String title;
    private Integer progress;
    private Integer percent;
    private Integer years;
    private String imageUrl;

    @Indexed
    private boolean active;
    @Indexed
    private int prg;
}