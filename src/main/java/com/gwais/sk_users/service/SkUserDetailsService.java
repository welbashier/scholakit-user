package com.gwais.sk_users.service;

import java.sql.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gwais.sk_users.dto.EmailRequest;
import com.gwais.sk_users.dto.RegistrationRequest;
import com.gwais.sk_users.model.SmUser;
import com.gwais.sk_users.repository.SkUserRepository;

@Service
public class SkUserDetailsService implements UserDetailsService {

    private static final int RANDOM_NUMERIC_LENGTH = 6;

	@Autowired
    private SkUserRepository userRepository;
    
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        SmUser user = userRepository.findByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        // Convert SmUser to a Spring Security User (UserDetails)
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,  // active
                true,  // accountNonExpired
                true,  // credentialsNonExpired
                true,  // accountNonLocked
                user.getRoles()  // roles/authorities
        );
    }


	public EmailRequest register(RegistrationRequest regRequest) {
		SmUser newUser = new SmUser();
		newUser.setFirstName(regRequest.getFirstName());
		newUser.setLastName(regRequest.getLastName());
		String tempPassword = generateTempPassword();
		newUser.setPassword(tempPassword);
		newUser.setUsername(regRequest.getEmailAddress());
		newUser.setAccountStatus("P");	// P=Pending
		
		String verificationLink = "http://localhost:8080/emailVerification.html";
		
		try {
			/* The database takes care of managing uniqueness */
			userRepository.save(newUser);
			
			return new EmailRequest(
					regRequest.getEmailAddress(), 
					regRequest.getFirstName(), 
					regRequest.getLastName(), 
					tempPassword, verificationLink);
			
		} catch (Exception e) {
			throw new DataIntegrityViolationException("Data integrity issue");
		}
	}
	
	
	public void updateLastLoggedIn(String username, Date lastLoggedOn) {
		SmUser user = userRepository.findByUsername(username);
        if (user != null) {
            user.setDateLastLogon(lastLoggedOn);
            userRepository.save(user);
        }
    }
	

	private String generateTempPassword() {
		String randomNumeric = RandomStringUtils.randomNumeric(RANDOM_NUMERIC_LENGTH);
		
		String encodedPassword = passwordEncoder.encode(randomNumeric);
		return encodedPassword;
	}
}