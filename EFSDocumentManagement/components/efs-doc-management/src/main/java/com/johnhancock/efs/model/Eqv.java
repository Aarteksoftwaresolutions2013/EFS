package com.johnhancock.efs.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Eqv
{
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String value;
}
