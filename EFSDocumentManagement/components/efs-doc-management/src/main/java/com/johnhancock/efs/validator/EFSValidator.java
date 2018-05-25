package com.johnhancock.efs.validator;

import com.johnhancock.efs.exception.EFSInternalServerException;
import com.johnhancock.efs.exception.EFSNotFoundException;
import com.johnhancock.efs.model.SearchRequestBodyModel;

public class EFSValidator {

    public static void validateArgumentUserNameAndPassword(String username, String password) throws EFSNotFoundException {
        checkUserNameAndPassword(username, password);
    }

    public static void validateArgumentUserNameAndPasswordAndSessionID(String username, String password, String sessionId) throws EFSNotFoundException {
        checkUserNameAndPassword(username, password);
        if (sessionId.isEmpty()) {
            throw new EFSNotFoundException("Session Id is empty");
        }
    }

    public static void validateArgumentUserNameAndPasswordAndAppKey(String username, String password, String appkey) throws EFSNotFoundException {
        checkUserNameAndPassword(username, password);
        if (appkey.isEmpty()) {
            throw new EFSNotFoundException("App Key is empty");
        }
    }

    public static void validateArgumentUserNamePasswordAndRequestBody(String username, String password,
                                                                      SearchRequestBodyModel requestbody) throws EFSNotFoundException {
        checkUserNameAndPassword(username, password);
        if (requestbody.getEqv().isEmpty()) {
            throw new EFSNotFoundException("Request Body is empty");
        }else if (requestbody.getEqv().get(0).getName().isEmpty() || requestbody.getEqv().get(0).getValue().isEmpty()){
            throw new EFSNotFoundException("Parameter Name or Value is empty");
        }
    }

    private static void checkUserNameAndPassword(String username, String password) throws EFSNotFoundException {
        if (username.isEmpty() || password.isEmpty()) {
            throw new EFSNotFoundException("UserId or Password is empty");
        }
    }
}