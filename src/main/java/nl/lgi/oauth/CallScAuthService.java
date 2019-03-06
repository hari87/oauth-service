package nl.lgi.oauth;

import lombok.extern.slf4j.Slf4j;
import nl.lgi.oauth.model.SC_Auth_Resp_Pojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.sun.activation.registries.LogSupport.log;

@Component
@Slf4j
public class CallScAuthService {

    @Autowired
    TokenDataService tokenDataService;

    @Autowired
    Rest_Controller rest_controller;


    public SC_Auth_Resp_Pojo invokeSC_GenerateToken() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestUrl
                = "https://login.microsoftonline.com/servicecruiser.onmicrosoft.com/oauth2/token";

        MultiValueMap<String, String> map =
                new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "password");
        map.add("client_id", "a3469b00-6cac-4e71-bcd5-4a454bc24be3");
        map.add("client_secret", "HJ5QdcGkyt3mhqTQhL4iPnET6pf8OLnj/9RPPxGt5Ro=");
        map.add("username", "ziggo@servicecruiser.onmicrosoft.com");
        map.add("password", "Qutu6616");
        map.add("resource", "https://qa-api.nextservicecruiser.com");

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<SC_Auth_Resp_Pojo> response =
                restTemplate.exchange(requestUrl,
                        HttpMethod.POST,
                        entity,
                        SC_Auth_Resp_Pojo.class);
        System.out.println("logging response:  " + response.getBody());
        System.out.println("log response code: " + response.getStatusCodeValue());


        return response.getBody();

    }

    public SC_Auth_Resp_Pojo  invokeSC_RefreshToken() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestUrl
                = "https://login.microsoftonline.com/servicecruiser.onmicrosoft.com/oauth2/token";

        MultiValueMap<String, String> map =
                new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "refresh_token");
        map.add("client_id", "a3469b00-6cac-4e71-bcd5-4a454bc24be3");
        map.add("client_secret", "HJ5QdcGkyt3mhqTQhL4iPnET6pf8OLnj/9RPPxGt5Ro=");
        map.add("refresh_token", tokenDataService.getRefreshToken(tokenDataService.fetchTokens()));
        map.add("resource", "https://qa-api.nextservicecruiser.com");

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<SC_Auth_Resp_Pojo> response =
                restTemplate.exchange(requestUrl,
                        HttpMethod.POST,
                        entity,
                        SC_Auth_Resp_Pojo.class);
        System.out.println("logging response:  " + response.getBody());
        System.out.println("log response code: " + response.getStatusCodeValue());


        return response.getBody();
    }



}
