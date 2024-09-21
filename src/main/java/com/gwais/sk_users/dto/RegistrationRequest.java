package com.gwais.sk_users.dto;

import java.util.Set;

public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private Set<String> roleDescriptions;
    

    // Default constructor
	public RegistrationRequest() {
	}
    
	public RegistrationRequest(String firstName, String lastName, String emailAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Set<String> getRoleDescriptions() {
		return roleDescriptions;
	}

	public void setRoleDescriptions(Set<String> roleDescriptions) {
		this.roleDescriptions = roleDescriptions;
	}

}
