package com.danieleautizi.service.personaldata.model.presentation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Education {

    private String id;

    private String school;
    private String title;
    private String description;
    private String schoolImage;
    private String schoolThumb;
    private String period;
    private String startYear;
    private String endYear;

    private boolean active;
    private int prg;

    private ZonedDateTime datetime;
    private ZonedDateTime lastUpdate;
}
