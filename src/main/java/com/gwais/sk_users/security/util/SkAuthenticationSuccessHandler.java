package com.gwais.sk_users.security.util;

import java.io.IOException;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.gwais.sk_users.service.SkUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class SkAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private final SkUserDetailsService userService;

	
    public SkAuthenticationSuccessHandler(SkUserDetailsService userService) {
        this.userService = userService;
    }

    // this is called automatically by framework for Form-Data
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String username = authentication.getName();// Update the lastLoggedIn field
        Date currentDate = new java.sql.Date(System.currentTimeMillis());
        userService.updateLastLoggedIn(username, currentDate);

        // Optionally return a JSON response
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // this is called manually for JSON objects handling
    public void onAuthenticationSuccessManual(HttpServletResponse response, 
    										  Authentication authentication) throws IOException {
        String username = authentication.getName();
        Date currentDate = new java.sql.Date(System.currentTimeMillis());
        userService.updateLastLoggedIn(username, currentDate);

        // Set response or return a token, etc.
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

