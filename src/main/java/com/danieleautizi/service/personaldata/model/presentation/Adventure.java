package com.danieleautizi.service.personaldata.model.presentation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adventure {

    private String id;
    private String articleUniquePath;

    private String title;
    private String category;
    private String section;
    private String tag;

    private String keywords;
    private String cssClass;
    private String image;
    private String icon;
    private String altImage;
    private String articleUrl;
    private String description;

    private String adventureType;

    private String staticUrl;
    private String viewType;
    private String mediaCssClass;

    private boolean active;
    private int prg;

    private ZonedDateTime datetime;
    private ZonedDateTime lastUpdate;

    private List<AdventureMedia> adventureMedia;

    private Adventure prev;
    private Adventure next;
}
