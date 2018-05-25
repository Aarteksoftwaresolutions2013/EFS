package com.johnhancock.efs.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class SearchRequestBodyModel {

    @Getter
    @Setter
    private List<SearchParamsModel> eqv;

}