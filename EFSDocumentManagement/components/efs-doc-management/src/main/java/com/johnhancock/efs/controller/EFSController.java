package com.johnhancock.efs.controller;

import com.jh.common.exception.ExceptionHandler;
import com.jh.common.logging.LogHandler;
import com.jh.common.model.ExceptionModel;
import com.jh.common.model.LogExceptionModel;
import com.johnhancock.efs.constant.EFSHeaderEnum;
import com.johnhancock.efs.constant.EFSURIConst;
import com.johnhancock.efs.constant.SystemConst;
import com.johnhancock.efs.exception.EFSInternalServerException;
import com.johnhancock.efs.exception.EFSNotFoundException;
import com.johnhancock.efs.model.SearchRequestBodyModel;
import com.johnhancock.efs.service.EFSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.johnhancock.efs.validator.EFSValidator.*;

@RestController
@RequestMapping(SystemConst.API_PRIFIX)
public class EFSController {

    LogHandler logHandler = new LogHandler();
    ExceptionHandler exceptionHandler = new ExceptionHandler();

    @Autowired
    private EFSService eFSService;

    @GetMapping(EFSURIConst.LOGON)
    public Mono<ResponseEntity<String>> eFSLogOn(@RequestHeader("APP-UserID") String username, @RequestHeader("APP-Password") String password) {
        LogExceptionModel logExceptionModel = getLogExceptionModel(EFSURIConst.LOGON);
        try {
            logHandler.Logging(logExceptionModel, new HttpHeaders());
            System.out.println("username = " + username);
            System.out.println("password = " + password);
            validateArgumentUserNameAndPassword(username, password);
            Map<EFSHeaderEnum, String> rp = new HashMap<>();
            setRequestheader(username, password, rp);
            setCommonParams(rp);
            return eFSService.eFSLogon(rp);
        } catch (RuntimeException ex) {
            System.out.println("Second Catch block");
            if (ex instanceof EFSInternalServerException) {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSInternalServerException(ex.getMessage());
            } else if (ex instanceof EFSNotFoundException) {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.NOT_FOUND.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSNotFoundException(ex.getMessage());
            } else {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSNotFoundException(ex.getMessage());
            }
        }
    }

    @GetMapping(EFSURIConst.LOGOFF)
    public Mono<ResponseEntity<String>> eFSLogOff(@RequestHeader("APP-SessionID") String sessionId, @RequestHeader("APP-UserID") String username,
                                                  @RequestHeader("APP-Password") String password) {
        LogExceptionModel logExceptionModel = getLogExceptionModel(EFSURIConst.SEARCH);
        try {
            logHandler.Logging(logExceptionModel, new HttpHeaders());
            validateArgumentUserNameAndPasswordAndSessionID(username, password, sessionId);
            Map<EFSHeaderEnum, String> rp = new HashMap<>();
            setRequestheader(username, password, rp);
            setCommonParams(rp);
            rp.put(EFSHeaderEnum.APP_SESSIONID, sessionId);
            return eFSService.eFSLogoff(rp);
        } catch (RuntimeException ex) {
            if (ex instanceof EFSInternalServerException) {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSInternalServerException(ex.getMessage());
            } else if (ex instanceof EFSNotFoundException) {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.NOT_FOUND.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSNotFoundException(ex.getMessage());
            } else {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSNotFoundException(ex.getMessage());
            }
        }
    }

    @PostMapping(EFSURIConst.SEARCH)
    public Mono<ResponseEntity<String>> eFSSearch(@RequestHeader("APP-UserID") String username, @RequestHeader("APP-Password") String password,
                                                  @RequestBody SearchRequestBodyModel srbm) {
        LogExceptionModel logExceptionModel = getLogExceptionModel(EFSURIConst.SEARCH);
        try {
            logHandler.Logging(logExceptionModel, new HttpHeaders());
            validateArgumentUserNamePasswordAndRequestBody(username, password, srbm);
            Map<EFSHeaderEnum, String> rp = new HashMap<>();
            setRequestheader(username, password, rp);
            setCommonParams(rp);
            return eFSService.eFSSearch(rp, srbm);
        } catch (RuntimeException ex) {
            if (ex instanceof EFSInternalServerException) {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSInternalServerException(ex.getMessage());
            } else if (ex instanceof EFSNotFoundException) {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.NOT_FOUND.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSNotFoundException(ex.getMessage());
            } else {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSNotFoundException(ex.getMessage());
            }

        }
    }

    @PostMapping(EFSURIConst.UPDATE)
    public Mono<ResponseEntity<String>> eFSUpdate(@RequestHeader("APP-Key") String key, @RequestHeader("APP-UserID") String username,
                                                  @RequestHeader("APP-Password") String password, @RequestHeader("APP-Etag") String etag, @RequestBody SearchRequestBodyModel srbm) {
        LogExceptionModel logExceptionModel = getLogExceptionModel(EFSURIConst.UPDATE);
        try {
            logHandler.Logging(logExceptionModel, new HttpHeaders());
            validateArgumentUserNamePasswordAndRequestBody(username, password, srbm);
            Map<EFSHeaderEnum, String> rp = new HashMap<>();
            rp.put(EFSHeaderEnum.APP_KEY, key);
            rp.put(EFSHeaderEnum.APP_ETAG, etag);
            setRequestheader(username, password, rp);
            setCommonParams(rp);
            return eFSService.eFSUpdate(rp, srbm);
        } catch (RuntimeException ex) {
            System.out.println("Second Catch block");
            if (ex instanceof EFSInternalServerException) {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSInternalServerException(ex.getMessage());
            } else if (ex instanceof EFSNotFoundException) {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.NOT_FOUND.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSNotFoundException(ex.getMessage());
            } else {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSNotFoundException(ex.getMessage());
            }
        }
    }

    @GetMapping(EFSURIConst.METADATA)
    public Mono<ResponseEntity<String>> eFSMetadata(@RequestHeader("APP-Key") String key, @RequestHeader("APP-UserID") String username,
                                                    @RequestHeader("APP-Password") String password) {
        LogExceptionModel logExceptionModel = getLogExceptionModel(EFSURIConst.METADATA);
        try {
            logHandler.Logging(logExceptionModel, new HttpHeaders());
            validateArgumentUserNameAndPasswordAndAppKey(username, password, key);
            Map<EFSHeaderEnum, String> rp = new HashMap<>();
            rp.put(EFSHeaderEnum.APP_KEY, key);
            setRequestheader(username, password, rp);
            setCommonParams(rp);
            return eFSService.eFSMetadata(rp);
        } catch (RuntimeException ex) {
            System.out.println("Second Catch block");
            if (ex instanceof EFSInternalServerException) {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSInternalServerException(ex.getMessage());
            } else if (ex instanceof EFSNotFoundException) {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.NOT_FOUND.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSNotFoundException(ex.getMessage());
            } else {
                exceptionHandler.ExceptionHandling(getExceptionModel(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex), new HttpHeaders(), ex, logExceptionModel);
                throw new EFSNotFoundException(ex.getMessage());
            }
        }
    }

    private void setRequestheader(String username, String passowrd, Map<EFSHeaderEnum, String> rp) {
        rp.put(EFSHeaderEnum.APP_USERNAME, username);
        rp.put(EFSHeaderEnum.APP_PASSWORD, passowrd);
    }

    private void setCommonParams(Map<EFSHeaderEnum, String> rp) {
        rp.put(EFSHeaderEnum.HMAC_KEY, "jsaSj3d8sJ22fdfG");
        rp.put(EFSHeaderEnum.SECRET_KEY, "ad426898f7774d92941yy3g30836aabc");
        rp.put(EFSHeaderEnum.APP_SESSIONID, "");
    }

    private LogExceptionModel getLogExceptionModel(String auditpoint) {
        LogExceptionModel logExceptionModel = new LogExceptionModel();
        logExceptionModel.setTransactionId("");
        logExceptionModel.setSourceSystemName("");
        logExceptionModel.setLogMessage("");
        logExceptionModel.setExceptionMessage("");
        logExceptionModel.setTransactionBefore("");
        logExceptionModel.setTransactionAfter("");
        logExceptionModel.setStackTrace("");
        logExceptionModel.setTargetSystem("");
        logExceptionModel.setTransExecDate(new Date());
        logExceptionModel.setAppid("");
        logExceptionModel.setSvcname("");
        logExceptionModel.setComponentname("");
        logExceptionModel.setSource("");
        logExceptionModel.setAuditpoint(auditpoint);
        logExceptionModel.setCategory("");
        logExceptionModel.setVersion("");

        return logExceptionModel;
    }

    private ExceptionModel getExceptionModel(String httpCode, Exception ex) {
        ExceptionModel exceptionResponse = new ExceptionModel();
        exceptionResponse.setHttpcode(httpCode);
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setCode(httpCode);
        return exceptionResponse;
    }
}