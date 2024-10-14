package com.gwais.sk_users.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.gwais.sk_users.model.SkRole;

@DataJpaTest
class SkRoleRepositoryTest {

	@Autowired
	private SkRoleRepository skRoleRepository;

	@Test
	public void checkAutowired() {
		assertNotNull(skRoleRepository);
	}
	
	@Test
	public void testSaveAllIterableOfS() {
        SkRole role1 = new SkRole("Admin", "Admin role with all privileges", "ROLE_ADMIN");
        SkRole role2 = new SkRole("User", "User role with limited access", "ROLE_USER");

        Set<SkRole> roles = new HashSet<>(Arrays.asList(role1, role2));
        Iterable<SkRole> savedRoles = skRoleRepository.saveAll(roles);

        assertThat(savedRoles).hasSize(2);
        assertThat(savedRoles).extracting(SkRole::getRoleCode).contains("ROLE_ADMIN", "ROLE_USER");
    }
	
	@Test
    public void testFindAllByIdIterableOfID() {
        SkRole savedRole1 = skRoleRepository.save(new SkRole("Admin", "Admin role", "ROLE_ADMIN"));
        SkRole savedRole2 = skRoleRepository.save(new SkRole("User", "User role", "ROLE_USER"));

        Set<Long> ids = new HashSet<>(Arrays.asList(savedRole1.getId(), savedRole2.getId()));
        Iterable<SkRole> foundRoles = skRoleRepository.findAllById(ids);

        assertThat(foundRoles).hasSize(2);
        assertThat(foundRoles).extracting(SkRole::getRoleCode).contains("ROLE_ADMIN", "ROLE_USER");
    }

	@Test
    public void testSave() {
        SkRole role = new SkRole("Moderator", "Moderator role with special privileges", "ROLE_MODERATOR");
        SkRole savedRole = skRoleRepository.save(role);

        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getId()).isNotNull();
        System.out.println("new role id = " + savedRole.getId() );
        assertThat(savedRole.getRoleCode()).isEqualTo("ROLE_MODERATOR");
    }
	
	@Test
    public void testDeleteById() {
        SkRole role = skRoleRepository.save(new SkRole("Admin", "Admin role", "ROLE_ADMIN"));
        Long roleId = role.getId();

        skRoleRepository.deleteById(roleId);
        Optional<SkRole> deletedRole = skRoleRepository.findById(roleId);

        assertTrue(deletedRole.isEmpty());
    }

}
