package com.gwais.sk_users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gwais.sk_users.model.SmUser;

public interface SkUserRepository extends JpaRepository<SmUser, Long>{

}
