package com.gwais.sk_users.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SkUserRepositoryTest {

	@Autowired
	private SkUserRepository skUserRepository;

	@Test
	public void checkAutowired() {
		assertNotNull(skUserRepository);
	}

}
