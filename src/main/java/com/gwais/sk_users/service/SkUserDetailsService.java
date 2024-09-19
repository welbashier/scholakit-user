package com.gwais.sk_users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gwais.sk_users.dto.AuthenticationRequest;
import com.gwais.sk_users.dto.RegistrationRequest;
import com.gwais.sk_users.model.SmUser;
import com.gwais.sk_users.repository.SkUserRepository;

@Service
public class SkUserDetailsService implements UserDetailsService {

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
		newUser.setUsername(regRequest.getEmalAddress());
		newUser.setAccountStatus("P");	// P=Pending
		
		// TODO check if unique or not
		SmUser savedUser = userRepository.save(newUser);
		return savedUser.getUserId();
	}


	public Long authenticate(AuthenticationRequest regRequest) {
		String passedInUsername = regRequest.getUsername();
		String passedInPassword = regRequest.getUsername();
        SmUser savedUser = userRepository.findByUsername(passedInUsername);
		String savedPassword = savedUser.getPassword();
		
		if (!passwordEncoder.matches(passedInPassword, savedPassword)) {
			throw new UsernameNotFoundException("User not found");
		}
		return savedUser.getUserId();
	}

	private String generateTempPassword() {
		// TODO Auto-generated method stub
		String encodedPassword = passwordEncoder.encode("welcome!");
		return encodedPassword;
	}
}