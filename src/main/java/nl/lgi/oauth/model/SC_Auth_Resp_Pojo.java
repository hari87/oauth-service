package nl.lgi.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SC_Auth_Resp_Pojo {
    private String token_type;
    private String scope;
    private String expires_in;
    private String ext_expires_in;
    private String expires_on;
    private String not_before;
    private String resource;
    private String access_token;
    private String refresh_token;



}
