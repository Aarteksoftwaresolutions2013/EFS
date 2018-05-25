package com.johnhancock.efs.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class SearchReponseModel {
    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private Key key;

}