package nl.lgi.oauth;

import nl.lgi.oauth.model.SC_Auth_Resp_Pojo;
import org.apache.camel.EndpointInject;
import org.apache.camel.FluentProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class Rest_Controller {

    @Autowired
    CallScAuthService call_sc_auth;

    @Autowired
    TokenDataService tokenDataService;

    @EndpointInject(uri = "direct:processServiceCruizerRequest")
    private FluentProducerTemplate producer;

    @RequestMapping("/sc/auth/create/token")
    public SC_Auth_Resp_Pojo generateToken(){
        SC_Auth_Resp_Pojo response = tokenDataService.fetchTokens();
        return response;
    }

    @RequestMapping("/sc/auth/create/tokenForce")
    public SC_Auth_Resp_Pojo generateTokenForce(){
        SC_Auth_Resp_Pojo response = tokenDataService.fetchTokensAndUpdateCache();
        return response;
    }

    @RequestMapping("/sc/auth/fetch/accessToken")
    public String getAccessToken(){
        String token = tokenDataService.getAccessToken(tokenDataService.fetchTokens());
        return token;
    }
    @RequestMapping("/sc/auth/fetch/refreshToken")
    public String getRefreshToken(){
        String token = tokenDataService.getRefreshToken(tokenDataService.fetchTokens());
        return token;
    }
    @RequestMapping("/sc/auth/update/accessToken")
    public SC_Auth_Resp_Pojo updateToken(){
        SC_Auth_Resp_Pojo response = tokenDataService.updateAccessToken();
        return response;
    }
    @PostMapping("/appointment/system/proposal")
    @RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    consumes = "application/json",
                    method = RequestMethod.POST, value = "/appointment/system/proposal")
    public String appointmentProposal(@RequestBody String inputData){
        String resp = producer.withBody(inputData).withHeader("request_meta", "post_create_appointment").request(String.class);
        return resp;
    }


}
