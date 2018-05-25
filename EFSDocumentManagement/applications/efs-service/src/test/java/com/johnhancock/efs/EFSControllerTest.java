package com.johnhancock.efs;

import com.johnhancock.efs.constant.EFSHeaderEnum;
import com.johnhancock.efs.constant.EFSURIConst;
import com.johnhancock.efs.constant.SystemConst;
import com.johnhancock.efs.model.HeaderParamsModel;
import com.johnhancock.efs.utils.EFSHeadersParamsUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static com.johnhancock.efs.constant.SystemConst.API_PRIFIX;
import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:junittestdata.properties")
public class EFSControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String EMPTY = "";

    Map<String, String> parms;

    @Value("${efs.username}")
    private String username;

    @Value("${efs.password}")
    private String password;

    @Value("${efs.appetag}")
    private String appETag;

    @Value("${efs.appkey}")
    private String appKey;

    @Value("${efs.searchJson}")
    private String searchJson;

    @Value("${efs.updateJson}")
    private String updateJson;


    @Before
    public void setUp() {

        parms = new HashMap<>();
    }

    @Test
    public void testEFSLogon() {
        ResponseEntity<String> result = getLogonResponseEntity();
        assertEquals("Logon API Result Faild", HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
    }


    private ResponseEntity<String> getLogonResponseEntity() {
        HttpHeaders headers = getHttpHeadersCommonParams(EMPTY);
        return restTemplate.exchange(API_PRIFIX + EFSURIConst.LOGON, GET, new HttpEntity<String>(EMPTY, headers), String.class);
    }

    @Test
    public void testEFSLogoff() {
        ResponseEntity<String> logonResponse = getLogonResponseEntity();
        String sessionId = logonResponse.getHeaders().get(EFSHeaderEnum.AUTH.getParam()).get(0);
        HttpHeaders headers = getHttpHeadersCommonParams(sessionId);
        ResponseEntity<String> result = restTemplate.exchange(API_PRIFIX + EFSURIConst.LOGOFF, GET, new HttpEntity<String>(EMPTY, headers), String.class);
        assertEquals("Logoff API Result Faild", HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    public void testEFSSearch() {
        HttpHeaders headers = getHttpHeadersCommonParams(EMPTY);
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<String> result = restTemplate.exchange(API_PRIFIX + EFSURIConst.SEARCH, POST, new HttpEntity<String>(searchJson, headers), String.class);
        assertEquals("Search API Result Faild", HttpStatus.OK, result.getStatusCode());
        System.out.println("Search BODY :" + result.getBody());
        assertNotNull("Search Request Body not null", result.getBody());
    }


    @Test
    public void testMetadata() {
        HttpHeaders headers = getHttpHeadersCommonParams(EMPTY);

        ResponseEntity<String> result = restTemplate.exchange(API_PRIFIX + EFSURIConst.METADATA, GET, new HttpEntity<String>(EMPTY, headers), String.class);
        assertEquals("Metadata API Result Faild", HttpStatus.OK, result.getStatusCode());
        assertNotNull("Metadata Request Body not null", result.getBody());
    }

    @Test
    public void testUpdate() {
        HttpHeaders headers = getHttpHeadersCommonParams(EMPTY);
        headers.setContentType(APPLICATION_JSON);
        ResponseEntity<String> result = restTemplate.exchange(API_PRIFIX + EFSURIConst.UPDATE, POST, new HttpEntity<String>(updateJson, headers), String.class);
        assertEquals("Update API Result Faild", HttpStatus.OK, result.getStatusCode());
        assertNotNull("Update Request Body not null", result.getBody());
    }

    @Test
    public void testGetData() {

        ResponseEntity<String> result = restTemplate.exchange("/jh/efs/getData", GET, new HttpEntity<String>("", new HttpHeaders()), String.class);
        assertEquals("success", result.getBody());
    }

    private HttpHeaders getHttpHeadersCommonParams(String sessionId) {
        parms.clear();
        HttpHeaders headers = new HttpHeaders();
        parms.put(EFSHeaderEnum.APP_USERNAME.getParam(), username);
        parms.put(EFSHeaderEnum.APP_PASSWORD.getParam(), password);
        parms.put(EFSHeaderEnum.APP_KEY.getParam(), appKey);
        parms.put(EFSHeaderEnum.APP_ETAG.getParam(), appETag);
        parms.put(EFSHeaderEnum.APP_SESSIONID.getParam(), sessionId);
        headers.setAll(parms);
        return headers;
    }


}