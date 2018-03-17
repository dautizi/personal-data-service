package com.danieleautizi.service.personaldata.model.presentation;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdventureMedia {

    private String id;

    private String mediaType;
    private String mediaPath;
    private String mediaUrl;
    private String title;
    private String alt;
    private String cssClass;

    private boolean active;
    private int prg;
}
