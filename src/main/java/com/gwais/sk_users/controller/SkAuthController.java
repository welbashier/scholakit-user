package com.gwais.sk_users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwais.sk_users.dto.AuthenticationRequest;
import com.gwais.sk_users.dto.EmailRequest;
import com.gwais.sk_users.dto.RegistrationRequest;
import com.gwais.sk_users.rabbitmq.MessageProducer;
import com.gwais.sk_users.security.util.JwtTokenUtil;
import com.gwais.sk_users.security.util.SkAuthenticationSuccessHandler;
import com.gwais.sk_users.service.SkUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class SkAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private SkUserDetailsService userDetailsService;

    @Autowired
    private SkAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private MessageProducer messageProducer;
    
    
    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(
    		@RequestBody RegistrationRequest regRequest
    		) throws Exception {
    	
    	try {
			/* 
			 * First, the JWT token must be present (passed-in)
			 * Second, the Security Filter kicks in to authenticate the token
			 * 		if the authentication fails it issues an exception an returns
			 * 		otherwise, it continues on..
			 * */
    		
    		// Get the logged-in user's security object
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    		
    		// If authorized, go register the new user
    	    if (authentication != null && authentication.getAuthorities().stream()
    	            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
    	    	
    	    	EmailRequest emailRequest = userDetailsService.register(regRequest);
    	    	
				messageProducer.sendEmailRequest(emailRequest);
    	    	
    	    } else {
    	        // User does not have the required authority
    	    	throw new AccessDeniedException("User does not have enough rights to perform process");
    	    }
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
    	
    	return ResponseEntity.ok("Thank you for registering. A verification email is sent out!");
    }
    
    /*
     * To test this from an API client, use Postman with the following steps:
     * 		1. Select "POST" as the method type of the request
     * 		2. For the URL, write: http://localhost:8013/api/auth/login
     * 		3. Select: Body > Raw > JSON, and type in:
				{
				    "username": "jsmith",
				    "password": "welcome!"
				}
	 *		4. Click "Send" button
	 *		5. You should get a (200 OK) response along with a JWT taken
	 *		6. That token can be used for subsequent requests (while not expired)
	 *
	 * To use that token in subsequent request:
	 * 		1. Go to: Auth > Auth Type: "Bearer Token" > token: <paste the JWT>
	 * 		2. Follow steps (1-4) above to send any request you want
	 * 
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(
    		@RequestBody AuthenticationRequest authRequest, HttpServletResponse response
    		) throws Exception {
    	String jwt;
    	
    	try {
			/* Filter kicks in, if JWT token is missing, filter is skipped for /login endpoint */

    		// Create authentication object from the passed-in form/request
    		String username = authRequest.getUsername();
    		String password = authRequest.getPassword();
    		UsernamePasswordAuthenticationToken authentication = 
    				new UsernamePasswordAuthenticationToken(username, password);

    		// Authenticate it. This includes calling loadUserByUsername() via framework
    		Authentication authenticate = authenticationManager.authenticate(authentication);
    		
    		// Call the success handler manually
    		authenticationSuccessHandler.onAuthenticationSuccessManual(response, authenticate);
    		
            // Create the JWT token
			String authenticatedUsername = authenticate.getName();
			jwt = jwtUtil.generateToken(authenticatedUsername);
			
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    	
        return ResponseEntity.ok(jwt);
    }
    
    
    @GetMapping("/emailVerification")
    public ResponseEntity<?> verifyEmailAddress(String token) throws Exception {
    	
    	if (token == null) {
    		return ResponseEntity
    				.status(HttpStatus.BAD_REQUEST)
    				.body("Token is missing!");
    	}
    	
    	try {
    		userDetailsService.verifyEmail(token);
			
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error is encountered!");
		}

    	return ResponseEntity.ok("Email Verified");
	}
}
