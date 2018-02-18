package com.danieleautizi.model.presentation;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private String id;

    private String detailPath;
    private String url;
    private String header;
    private String image;
    private String thumb;
    private String title;
    private String summary;
    private String body;
    private String author;

    private boolean active;
    private int prg;

    private ZonedDateTime datetime;
    private ZonedDateTime lastUpdate;

    private List<Image> images;
}
