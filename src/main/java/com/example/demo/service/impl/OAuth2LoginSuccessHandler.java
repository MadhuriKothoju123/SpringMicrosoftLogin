package com.example.demo.service.impl;


import com.example.demo.entity.RegistrationSource;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserRole;
import com.example.demo.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component

public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
    private UserService userService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {


        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        System.out.println(oAuth2AuthenticationToken);
        System.out.println(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
        if ("azure".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
   
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
         
            Map<String, Object> attributes = principal.getAttributes();
            Collection<? extends GrantedAuthority> attributes1 = principal.getAuthorities();
       
            String email = attributes.getOrDefault("preferred_username", "").toString();
          
            String name = attributes.getOrDefault("name", "").toString();
            userService.findByEmail(email)
                    .ifPresentOrElse(user -> {
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                attributes, "name");
                        
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                        System.out.println(securityAuth);
                    }, () -> {
                        UserEntity userEntity = new UserEntity();
                        userEntity.setRole(UserRole.ROLE_USER);
                        userEntity.setEmail(email);
                        userEntity.setName(name);
                        userEntity.setSource(RegistrationSource.AZURE);
                        userService.save(userEntity);
                        oAuth2AuthenticationToken.getAuthorities().add(new CustomRole("ROLE_USER"));
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                attributes, "name");
                        System.out.println(newUser);
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    });
        }
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrl);
        super.onAuthenticationSuccess(request, response, authentication);
    }
    
     class CustomRole implements GrantedAuthority {
        private static final long serialVersionUID = 1L;
		private String role;

        public CustomRole(String role) {
            this.role = role;
        }

        @Override
        public String getAuthority() {
            return role;
        }
}
}
