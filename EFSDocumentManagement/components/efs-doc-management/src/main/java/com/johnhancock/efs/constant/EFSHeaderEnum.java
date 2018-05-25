package com.johnhancock.efs.constant;

public enum EFSHeaderEnum {
    SIGN("EFS-Sign"), DATE("Date"), KEY("EFS-Key"), AUTH("EFS-Auth"), HMAC_KEY("HMAC-Key"), SECRET_KEY("SECRET-Key"), APP_USERNAME("APP-UserID"),
    APP_PASSWORD("APP-Password"), APP_KEY("APP-Key"), APP_ETAG("APP-Etag"), APP_SESSIONID("APP-SessionID");


    private String param;

    EFSHeaderEnum(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

}
