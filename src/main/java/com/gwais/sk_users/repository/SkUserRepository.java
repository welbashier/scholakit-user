package com.gwais.sk_users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gwais.sk_users.model.SmUser;

@Repository
public interface SkUserRepository extends JpaRepository<SmUser, Long>{
	
    // Custom query method to find a user by username
    SmUser findByUsername(String username);

	SmUser findByPassword(String token);

}
