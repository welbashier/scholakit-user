package com.gwais.sk_users.dto;

public class EmailRequest {

    private String emailAddress;
    private String firstName;
    private String lastName;
    private String pin;
    private String verificationLink;

    // Constructor with validation
    public EmailRequest(
    		String emailAddress, 
    		String firstName, 
    		String lastName, 
    		String pin, 
    		String verificationLink) {
        if (isNullOrEmpty(emailAddress) 
        		|| isNullOrEmpty(firstName) 
        		|| isNullOrEmpty(lastName) 
        		|| isNullOrEmpty(pin) 
        		|| isNullOrEmpty(verificationLink)) {
            throw new IllegalArgumentException("All fields are required and must not be null or empty.");
        }
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.verificationLink = verificationLink;
    }

    // Helper method to check if a String is null or empty
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Getters and Setters
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        if (isNullOrEmpty(emailAddress)) {
            throw new IllegalArgumentException("Email address is required.");
        }
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (isNullOrEmpty(firstName)) {
            throw new IllegalArgumentException("First name is required.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (isNullOrEmpty(lastName)) {
            throw new IllegalArgumentException("Last name is required.");
        }
        this.lastName = lastName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        if (isNullOrEmpty(pin)) {
            throw new IllegalArgumentException("PIN is required.");
        }
        this.pin = pin;
    }

    public String getVerificationLink() {
        return verificationLink;
    }

    public void setVerificationLink(String verificationLink) {
        if (isNullOrEmpty(verificationLink)) {
            throw new IllegalArgumentException("Verification link is required.");
        }
        this.verificationLink = verificationLink;
    }

    // toString method for convenient output
    @Override
    public String toString() {
        return "EmailRequest{" +
                "emailAddress='" + emailAddress + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pin='" + pin + '\'' +
                ", verificationLink='" + verificationLink + '\'' +
                '}';
    }
}
