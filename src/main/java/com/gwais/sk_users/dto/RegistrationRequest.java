package com.gwais.sk_users.dto;

public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String emalAddress;
    

    // Default constructor
	public RegistrationRequest() {
	}
    
	public RegistrationRequest(String firstName, String lastName, String emalAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emalAddress = emalAddress;
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
	
	public String getEmalAddress() {
		return emalAddress;
	}
	
	public void setEmalAddress(String emalAddress) {
		this.emalAddress = emalAddress;
	}

}
