package com.johnhancock.efs.utils;

import com.johnhancock.efs.config.ClientHttpRequestFactory;
import com.johnhancock.efs.constant.EFSURIConst;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RESTClientUtil {

    public static ResponseEntity<String> callRESTAPI(RestTemplate restTemplate, HttpHeaders headers, String endPoint, HttpMethod method, String body) throws RuntimeException {

        restTemplate.setRequestFactory(ClientHttpRequestFactory.getInstance());

        HttpEntity<String> entity = new HttpEntity<String>(body, headers);

        String url = "https://efsjhann-np.dstcorp.net/testapp/EFSWS/api/v1/action" + endPoint;

        ResponseEntity<String> result = null;
        result = restTemplate.exchange(url, method, entity, String.class);

        result = new ResponseEntity<String>((result.getBody() == null) ? "" : result.getBody().toString(), (EFSURIConst.LOGON.contains(endPoint)) ? result.getHeaders() :
                new HttpHeaders(), HttpStatus.OK);
        return result;
    }
}