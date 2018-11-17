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
public class WorkExperience {

    private String id;

    private String role;
    private String company;
    private String description;
    private String period;
    private String companyImage;
    private String companyThumb;

    private String startYear;
    private String endYear;

    private boolean active;
    private int prg;

    private ZonedDateTime datetime;
    private ZonedDateTime lastUpdate;
}