package com.aaas.springbootoauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


@EnableWebSecurity
public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private  OAuthConfig oAuthConfig;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers("/","/error","/css/**")
                        .permitAll()
                        .anyRequest().authenticated()
                );

      http.oauth2Login(oauth2 -> oauth2
              .userInfoEndpoint(userInfo -> userInfo
                      .oidcUserService(this.oidcUserService())

                )
            );
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        OidcUserService oidcUserService = new OidcUserService();
        oidcUserService.setOauth2UserService(getCustomOAuthUserService());
        return oidcUserService;
    }


    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> getCustomOAuthUserService(){
        return  new CustomOAuthUserService(restOperations(), getNimbusJwtDecoder());
    }

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }


    @Bean
    public NimbusJwtDecoder getNimbusJwtDecoder() {
       return NimbusJwtDecoder.withJwkSetUri(oAuthConfig.getIssueUri() +"/jwks").build();
    }


}
