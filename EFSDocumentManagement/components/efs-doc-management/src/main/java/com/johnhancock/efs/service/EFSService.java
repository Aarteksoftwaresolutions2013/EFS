package com.johnhancock.efs.service;

import com.johnhancock.efs.constant.EFSHeaderEnum;
import com.johnhancock.efs.model.HeaderParamsModel;
import com.johnhancock.efs.model.SearchRequestBodyModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public interface EFSService {

    public Mono<ResponseEntity<String>> eFSLogon(Map<EFSHeaderEnum, String> rp) throws RuntimeException;
    public Mono<ResponseEntity<String>> eFSLogoff(Map<EFSHeaderEnum, String> rp);
    public Mono<ResponseEntity<String>> eFSSearch(Map<EFSHeaderEnum, String> rp, SearchRequestBodyModel srbm) throws RuntimeException;
    public Mono<ResponseEntity<String>> eFSUpdate(Map<EFSHeaderEnum, String> rp, SearchRequestBodyModel srbm) throws RuntimeException;
    public Mono<ResponseEntity<String>> eFSMetadata(Map<EFSHeaderEnum, String> rp) throws RuntimeException;
    public String efsTemData(Map<EFSHeaderEnum, String> rp);
}
