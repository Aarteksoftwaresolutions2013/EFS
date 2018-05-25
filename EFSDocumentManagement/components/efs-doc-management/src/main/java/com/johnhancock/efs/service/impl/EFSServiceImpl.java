package com.johnhancock.efs.service.impl;

import com.johnhancock.efs.constant.EFSHeaderEnum;
import com.johnhancock.efs.constant.EFSURIConst;
import com.johnhancock.efs.model.HeaderParamsModel;
import com.johnhancock.efs.model.SearchRequestBodyModel;
import com.johnhancock.efs.service.EFSService;
import com.johnhancock.efs.utils.EFSHeadersParamsUtils;
import com.johnhancock.efs.utils.RESTClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.johnhancock.efs.utils.EFSHeadersParamsUtils.convertToJson;

@Repository("eFSService")
public class EFSServiceImpl implements EFSService {

    @Autowired
    private RestTemplate restTemplate;

    private String sessionId(Map<EFSHeaderEnum, String> rp){
        Map<String, String> parms = new HashMap<>();
        HttpHeaders headers = getHttpHeadersCommonParams(rp, parms);
        ResponseEntity<String> result = RESTClientUtil.callRESTAPI(restTemplate, headers, EFSURIConst.LOGON, HttpMethod.PUT, "parameters");
        return result.getHeaders().get(EFSHeaderEnum.AUTH.getParam()).get(0);
    }

    @Override
    public Mono<ResponseEntity<String>> eFSLogon(Map<EFSHeaderEnum, String> rp) throws RuntimeException {
        Map<String, String> parms = new HashMap<>();
        HttpHeaders headers = getHttpHeadersCommonParams(rp, parms);
        ResponseEntity<String> result = RESTClientUtil.callRESTAPI(restTemplate, headers, EFSURIConst.LOGON, HttpMethod.PUT, "parameters");
        return Mono.just(result);
    }

    private HttpHeaders getHttpHeadersCommonParams(Map<EFSHeaderEnum, String> rp, Map<String, String> parms) {
        parms.clear();
        HttpHeaders headers = new HttpHeaders();
        HeaderParamsModel hpm = EFSHeadersParamsUtils.getHeaderParams(rp);
        parms.put(EFSHeaderEnum.AUTH.getParam(), hpm.getEFSAuth());
        parms.put(EFSHeaderEnum.KEY.getParam(), hpm.getEFSKey());
        parms.put(EFSHeaderEnum.SIGN.getParam(), hpm.getEFSSign());
        parms.put(EFSHeaderEnum.DATE.getParam(), hpm.getEFSDate());

        headers.setAll(parms);
        return headers;
    }

    private HttpHeaders getHttpHeadersCommonParams(Map<EFSHeaderEnum, String> rp, Map<String, String> parms,String sessionId) {
        parms.clear();
        HttpHeaders headers = new HttpHeaders();
        HeaderParamsModel hpm = EFSHeadersParamsUtils.getHeaderParams(rp);
        parms.put(EFSHeaderEnum.AUTH.getParam(), sessionId);
        parms.put(EFSHeaderEnum.KEY.getParam(), hpm.getEFSKey());
        parms.put(EFSHeaderEnum.SIGN.getParam(), hpm.getEFSSign());
        parms.put(EFSHeaderEnum.DATE.getParam(), hpm.getEFSDate());
        headers.setAll(parms);
        return headers;
    }

    @Override
    public Mono<ResponseEntity<String>> eFSLogoff(Map<EFSHeaderEnum, String> rp) {
        Map<String, String> parms = new HashMap<>();
        HttpHeaders headers = getHttpHeadersCommonParams(rp, parms);
       headers.setContentType(MediaType.APPLICATION_ATOM_XML);

        ResponseEntity<String> result = RESTClientUtil.callRESTAPI(restTemplate, headers, EFSURIConst.LOGOFF, HttpMethod.PUT, "parameters");
        return Mono.just(result);
    }

    @Override
    public Mono<ResponseEntity<String>> eFSSearch(Map<EFSHeaderEnum, String> rp, SearchRequestBodyModel srbm) throws RuntimeException{
        Map<String, String> parms = new HashMap<>();
        HttpHeaders headers = getHttpHeadersCommonParams(rp, parms);
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> acceptMediaType = new ArrayList<>();
        acceptMediaType.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptMediaType);
        ResponseEntity<String> result = RESTClientUtil.callRESTAPI(restTemplate, headers, EFSURIConst.SEARCH, HttpMethod.PUT, convertToJson(srbm));
        return Mono.just(result);
    }


    @Override
    public Mono<ResponseEntity<String>> eFSUpdate(Map<EFSHeaderEnum, String> rp, SearchRequestBodyModel srbm) throws RuntimeException{
        Map<String, String> parms = new HashMap<>();
        HttpHeaders headers = getHttpHeadersCommonParams(rp, parms);
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> acceptMediaType = new ArrayList<>();
        acceptMediaType.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptMediaType);

        String bindUri = EFSURIConst.UPDATE + prepareUpdateURI(rp.get(EFSHeaderEnum.APP_KEY), rp.get(EFSHeaderEnum.APP_ETAG));

        ResponseEntity<String> result = RESTClientUtil.callRESTAPI(restTemplate, headers, bindUri, HttpMethod.POST, convertToJson(srbm));
        return Mono.just(result);
    }

    @Override
    public Mono<ResponseEntity<String>> eFSMetadata(Map<EFSHeaderEnum, String> rp) throws RuntimeException{

        Map<String, String> parms = new HashMap<>();
        HttpHeaders headers = getHttpHeadersCommonParams(rp, parms);
        headers.setContentType(MediaType.APPLICATION_ATOM_XML);
        String bindUri = EFSURIConst.METADATA_WITHOUT_KEY + rp.get(EFSHeaderEnum.APP_KEY);

        List<MediaType> acceptMediaType = new ArrayList<>();
        acceptMediaType.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptMediaType);

        ResponseEntity<String> result = RESTClientUtil.callRESTAPI(restTemplate, headers, bindUri, HttpMethod.GET, "");
        return Mono.just(result);
    }

    private String prepareUpdateURI(String key, String etag) {
        return String.format("/%s?etag=%s", key, etag);
    }

    @Override
    public String efsTemData(Map<EFSHeaderEnum, String> rp) {
        Map<String, String> parms = new HashMap<>();
        getHttpHeadersCommonParams(rp, parms);
        return "";
    }
}