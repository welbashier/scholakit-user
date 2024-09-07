package com.gwais.sk_users.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "SM_ROLE")
public class SkRole {
	
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
}
