package com.gwais.sk_users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwais.sk_users.dto.AuthenticationRequest;
import com.gwais.sk_users.dto.RegistrationRequest;
import com.gwais.sk_users.security.util.JwtTokenUtil;
import com.gwais.sk_users.service.SkUserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class SkAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private SkUserDetailsService userDetailsService;

    
    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(
    		@RequestBody RegistrationRequest regRequest) throws Exception {
    	Long registeredId = 0l;
    	try {
			/* First, the JWT token must be present (passed-in)
			 * Second, the Security Filter kicks in to authenticate the token
			 * 		if the authentication fails it issues an exception an returns
			 * 		otherwise, it continues on..
			 * */
    		
    		// Authorized, go register the new user
			registeredId = userDetailsService.register(regRequest);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
    	
    	return ResponseEntity.ok(registeredId);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try {
			/* Filter kicks in, if JWT token is missing, filter is skipped for /login endpoint */

    		// Create authentication object from the passed-in form/request
    		String username = authRequest.getUsername();
    		String password = authRequest.getPassword();
    		UsernamePasswordAuthenticationToken authentication = 
    				new UsernamePasswordAuthenticationToken(username, password);

    		// Authenticate it
    		authenticationManager.authenticate(authentication);
			
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Authorized, load the user info
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        
        // Create the JWT token and return it
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }
}
