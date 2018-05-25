package com.johnhancock.efs.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class SearchRequestModel {
    @Getter
    @Setter
    private Eqv[] eqv;

}