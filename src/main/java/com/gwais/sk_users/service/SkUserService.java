package com.gwais.sk_users.service;

import java.util.List;

import com.gwais.sk_users.model.SmUser;

public interface SkUserService {

	List<SmUser> findAll();

	SmUser findOneById(Long id);

	Long deleteOneById(Long id);

	SmUser modifyOne(SmUser changedCourse);

	SmUser insertOne(SmUser changedCourse);
}
