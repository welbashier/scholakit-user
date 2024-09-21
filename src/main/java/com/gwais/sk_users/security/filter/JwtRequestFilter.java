package com.gwais.sk_users.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gwais.sk_users.security.util.JwtTokenUtil;
import com.gwais.sk_users.service.SkUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    
	@Autowired
    private final SkUserDetailsService myUserDetailsService;

	@Autowired
    private final JwtTokenUtil jwtTokenUtil;

    
    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, SkUserDetailsService myUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.myUserDetailsService = myUserDetailsService;
    }

    
    @Override
    protected void doFilterInternal(
    		HttpServletRequest request, 
    		HttpServletResponse response, 
    		FilterChain chain) throws ServletException, IOException 
    {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtTokenUtil.extractUsername(token);
        }

        // if username is passed-in but not authenticated yet: validate against the database
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails savedUserDetails = myUserDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(token, savedUserDetails)) {
            	// valid user
            	// (1) create an authentication object for that user (in Spring)
                UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                    		savedUserDetails, 
                    		null, /* password not needed for already authenticated user */
                    		savedUserDetails.getAuthorities());
                
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // (2) Add the authentication object to Spring Security Context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }

}
