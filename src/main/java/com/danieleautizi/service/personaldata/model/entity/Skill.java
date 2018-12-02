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
public class Skill {

    @Id
    private ObjectId id;

    @Indexed
    private String groupName;
    @Indexed
    private int groupPrg;

    private String title;
    private Integer progress;
    private Integer percentage;
    private Integer years;
    private LocalDateTime since;
    private String imageUrl;

    @Indexed
    private boolean active;
    @Indexed
    private int prg;

    @Indexed
    private LocalDateTime datetime;
    @Indexed
    private LocalDateTime lastUpdate;
}
