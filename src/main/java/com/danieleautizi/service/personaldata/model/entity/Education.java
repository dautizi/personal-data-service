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
public class Education {

    @Id
    private ObjectId id;

    @Indexed
    private String school;
    private String title;
    private String description;
    private String schoolImage;
    private String schoolThumb;
    private String startYear;
    private String endYear;

    @Indexed
    private boolean active;
    @Indexed
    private int prg;

    @Indexed
    private LocalDateTime datetime;
    @Indexed
    private LocalDateTime lastUpdate;
}
