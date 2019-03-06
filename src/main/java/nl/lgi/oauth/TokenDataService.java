package nl.lgi.oauth;

import nl.lgi.oauth.model.SC_Auth_Resp_Pojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author hari
 */
@Component
public class TokenDataService {

    @Autowired
    CallScAuthService call_sc_auth;

    @Cacheable(value = "tokens", key = "#token.access_token")
    public String getAccessToken(SC_Auth_Resp_Pojo token){
        return token.getAccess_token();
    }

    @Cacheable(value = "tokens", key = "#token.refresh_token")
    public String getRefreshToken(SC_Auth_Resp_Pojo token){
        return token.getRefresh_token();
    }

    @CachePut(value= "tokens", key = "#token.access_token")
    public SC_Auth_Resp_Pojo updateAccessToken(SC_Auth_Resp_Pojo token){
        SC_Auth_Resp_Pojo sc_auth_resp_pojo = new SC_Auth_Resp_Pojo();
         sc_auth_resp_pojo.setAccess_token(token.getAccess_token());
         return sc_auth_resp_pojo;
    }

    @Cacheable("tokens")
    public SC_Auth_Resp_Pojo  fetchTokens(){
        return  call_sc_auth.invokeSC_GenerateToken();
    }

    @CachePut(value = "tokens")
    public SC_Auth_Resp_Pojo  updateAccessToken(){
         return  call_sc_auth.invokeSC_RefreshToken();
    }

    @CachePut("tokens")
    public SC_Auth_Resp_Pojo  fetchTokensAndUpdateCache(){
        return  call_sc_auth.invokeSC_GenerateToken();
    }


}
