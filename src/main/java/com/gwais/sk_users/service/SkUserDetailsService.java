package com.gwais.sk_users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gwais.sk_users.model.SmUser;
import com.gwais.sk_users.repository.SkUserRepository;

@Service
public class SkUserDetailsService implements UserDetailsService {

    @Autowired
    private SkUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        SmUser user = userRepository.findByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        return new org.springframework.security.core.userdetails
        		.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}