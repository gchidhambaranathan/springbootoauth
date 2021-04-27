# springbootoauth
Spring boot security integration for OIDC(OAUTH2) authorization code flow  

This is to intgerate spring application with external identity provider to authenticate users for spring application.

This leverages spring security module for integration.

This project has one custom implementation on top of oauth2 spring security module, where the openid /userinfo endpoint will be resulted as signed JWT token. As per open id specification
(https://openid.net/specs/openid-connect-core-1_0.html#UserInfo) the userinfo end point response will be plain JSON object or singed JWT based on configuration at 
openid client level. If this output is signed then framework fails to parse it. Now this issue is addressed by writing custom oauth2 user service and integrated with spring security


To successfully deploy this application, we need to register with one of IDP vendors and replace with real values in  the following items "<<>>" in application.yaml file.

server:
  port: 8445
  ssl:
#    key-store: classpath: <<.p12File>>
#    key-store-password: <<secret of keystore>>
    key-store-type: pkcs12
    key-store-alias: https
spring:
  logging:
    level:
      org.springframework.security: DEBUG
  security:
    oauth2:
      client:
        registration:
          idsvr:
#            client-name: <<openid client name>>
#            client-id: <<openid client id>>
#            client-secret: <<openid client>>
            authorization-grant-type: authorization_code
#            redirect-uri: <<redirect URL>>
            scope: openid,profile
        provider:
          idsvr:
#            issuer-uri: <<IDP issuer URL to get all openid configuration>>



Next important things , for security purpose the keystore file which was used for ssl https(s) communication is not uploaded in github. So requesting to create one and configure 
accordingly in application.yaml file.




