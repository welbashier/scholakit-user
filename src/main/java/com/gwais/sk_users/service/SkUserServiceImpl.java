package com.gwais.sk_users.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwais.sk_users.model.SmUser;
import com.gwais.sk_users.repository.SkUserRepository;

@Service
public class SkUserServiceImpl implements SkUserService {

	@Autowired
	private SkUserRepository userRepository;
	
	@Override
	public List<SmUser> findAll() {
		return userRepository.findAll();
	}

	@Override
	public SmUser findOneById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public Long deleteOneById(Long id) {
		// Hard-deletion
		checkExistanceById(id);
		userRepository.deleteById(id);
		return id;
	}

	@Override
	public SmUser modifyOne(SmUser changedUser) {
		checkExistanceById(changedUser.getUserId());
		return userRepository.save(changedUser);
	}

	@Override
	public SmUser insertOne(SmUser newUser) {
		return userRepository.save(newUser);
	}

	private void checkExistanceById(Long id) {
		Optional<SmUser> aCourse = userRepository.findById(id);
		if (!aCourse.isPresent()) {
			throw new NoSuchElementException("Record with ID " + id + " not found");
		}
	}

}
