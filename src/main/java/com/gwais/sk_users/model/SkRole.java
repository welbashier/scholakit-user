package com.gwais.sk_users.model;

import java.io.Serializable;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "SM_ROLE")
public class SkRole implements GrantedAuthority, Serializable {
	
	private static final long serialVersionUID = -4663757502622001269L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleSeqGen")
    @SequenceGenerator(name = "roleSeqGen", sequenceName = "SM_ROLE_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ROLE_DESCRIPTION", length = 50, nullable = false)
    private String roleDescription;

    @Column(name = "COMMENTS", length = 1000)
    private String comments;

    @Column(name = "ROLE_CODE", length = 20, nullable = false, unique = true)
    private String roleCode;
    
    @ManyToMany(mappedBy = "roles")  // This field is mapped by the "roles" field in the User entity
    private Set<SmUser> users;
    
    // Constructors
    public SkRole() {}

    public SkRole(String roleDescription, String comments, String roleCode) {
        this.roleDescription = roleDescription;
        this.comments = comments;
        this.roleCode = roleCode;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

	@Override
	public String getAuthority() {
        return roleCode;
	}

    public Set<SmUser> getUsers() {
        return users;
    }

    public void setUsers(Set<SmUser> users) {
        this.users = users;
    }

	@Override
	public String toString() {
		return "SkRole [id=" + id + ", roleDescription=" + roleDescription + ", comments=" + comments + ", roleCode="
				+ roleCode + ", users=" + users + "]";
	}
    
}
