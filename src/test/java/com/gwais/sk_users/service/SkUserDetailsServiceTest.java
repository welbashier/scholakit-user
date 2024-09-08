package com.gwais.sk_users.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gwais.sk_users.model.SkRole;
import com.gwais.sk_users.model.SmUser;
import com.gwais.sk_users.repository.SkUserRepository;

@ExtendWith(MockitoExtension.class)
class SkUserDetailsServiceTest {

	@Mock
    private SkUserRepository userRepository;

    @InjectMocks
    private SkUserDetailsService myUserDetailsService;

    private SmUser testUser;
    //private UserDetails myUserDetails;

    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up a sample user with roles
        SkRole roleAdmin = new SkRole("Admin", "Admin role", "ROLE_ADMIN");
        SkRole roleUser = new SkRole("User", "User role", "ROLE_USER");

        testUser = new SmUser();
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setRoles(Set.of(roleAdmin, roleUser)); // Assign the roles to the user
    }

    
    @Test
    void testLoadUserByUsername_Success() {
        // Mock the behavior of userRepository to return the testUser
        when(userRepository.findByUsername("testuser")).thenReturn(testUser);

        // Call the method to be tested
        UserDetails userDetails = myUserDetailsService.loadUserByUsername("testuser");

        // Assertions
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("testuser");
        assertThat(userDetails.getPassword()).isEqualTo("password123");
        assertThat(userDetails.getAuthorities()).hasSize(2); // Admin and User roles
        assertThat(userDetails.getAuthorities()).extracting("authority").contains("ROLE_ADMIN", "ROLE_USER");
    }

    
    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Mock the behavior of userRepository to return an empty Optional (user not found)
		when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

        // Expect UsernameNotFoundException when the user is not found
        assertThrows(UsernameNotFoundException.class, () -> {
            myUserDetailsService.loadUserByUsername("nonexistentuser");
        });
    }

}
