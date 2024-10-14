package com.gwais.sk_users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gwais.sk_users.model.SkRole;

@Repository
public interface SkRoleRepository extends JpaRepository<SkRole, Long>{
	
}
