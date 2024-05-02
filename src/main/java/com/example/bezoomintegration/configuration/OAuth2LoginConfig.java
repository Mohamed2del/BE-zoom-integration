//package com.example.bezoomintegration.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
//
//@Configuration
//public class OAuth2LoginConfig {
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(this.zoomClientRegistration());
//    }
//
//    private ClientRegistration zoomClientRegistration() {
//        // Set up your Zoom client registration details here
//        return ClientRegistration.withRegistrationId("zoom")
//                .clientId("g57u9SRyXm2vijAGRQ")
//                .clientSecret("IGifX2J6FntlkoGdzXWJukN57pUUHsOZ")
//                .scope("meeting:write")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("http://localhost:4200/")
//                .authorizationUri("https://zoom.us/oauth/authorize") //
//                .tokenUri("https://zoom.us/oauth/token")
//                .userInfoUri("https://api.zoom.us/v2/users/me")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .clientName("Zoom")
//                .build();
//    }
//}
