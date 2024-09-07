package com.gwais.sk_users.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gwais.sk_users.model.SmUser;
import com.gwais.sk_users.repository.SkUserRepository;

public class SkUserDetailsService implements UserDetailsService {

    @Autowired
    private SkUserRepository userRepository; // Assume you have a UserRepository for fetching user data

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SmUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}