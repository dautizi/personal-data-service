package com.danieleautizi.model.presentation;

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
public class Skill {

    private String id;

    private String groupName;
    private String title;
    private Integer progress;
    private Integer percent;
    private Integer years;
    private String imageUrl;

    private boolean active;
    private int prg;
}
