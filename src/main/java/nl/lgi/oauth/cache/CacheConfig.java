package nl.lgi.oauth.cache;


import nl.lgi.oauth.model.SC_Auth_Resp_Pojo;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/*@Configuration
@EnableCaching*/
public class CacheConfig {

 /*   @Bean
    SC_Auth_Resp_Pojo sc_auth_resp_pojo(){
        return new SC_Auth_Resp_Pojo();
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("tokens")));
        return cacheManager;

    }*/

}
