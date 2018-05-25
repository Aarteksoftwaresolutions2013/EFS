package com.johnhancock.efs.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class HeaderParamsModel {

    @Getter
    @Setter
    private String EFSAuth;

    @Getter
    @Setter
    private String EFSKey;

    @Getter
    @Setter
    private String EFSDate;

    @Getter
    @Setter
    private String EFSSign;
}
