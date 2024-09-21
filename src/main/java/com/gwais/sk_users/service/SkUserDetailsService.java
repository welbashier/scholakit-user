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
        
        // switch `SmUser` to `UserDetails` to be handled by the framework
        return new org.springframework.security.core.userdetails
        		.User(user.getUsername(), user.getPassword(), user.getRoles());
    }


	public Long register(RegistrationRequest regRequest) {
		SmUser newUser = new SmUser();
		newUser.setFirstName(regRequest.getFirstName());
		newUser.setLastName(regRequest.getLastName());
		newUser.setPassword(generateTempPassword());
		// TODO save the temp password as PIN_NUMBER 
		// along with PIN_EXPIRED_DATE, in table SM_USER
		newUser.setUsername(regRequest.getEmailAddress());
		newUser.setAccountStatus("P");	// P=Pending
		
		try {
			
			/* The database takes care of managing uniqueness */
			SmUser savedUser = userRepository.save(newUser);
			return savedUser.getUserId();
			
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