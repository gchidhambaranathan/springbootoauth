package com.aaas.springbootoauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthConfig {

    @Value("${spring.security.oauth2.client.provider.idsvr.issuer-uri}")
    private String issueUri;

    public String getIssueUri() {
        return issueUri;
    }
}
