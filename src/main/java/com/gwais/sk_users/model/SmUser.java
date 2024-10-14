package com.gwais.sk_users.model;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "SM_USER")
public class SmUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    @SequenceGenerator(name = "userSeqGen", sequenceName = "SM_USER_SEQ", allocationSize = 1)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "FIRST_NAME", length = 30)
    private String firstName;

    @Column(name = "SECOND_NAME", length = 30)
    private String secondName;

    @Column(name = "THIRD_NAME", length = 30)
    private String thirdName;

    @Column(name = "LAST_NAME", length = 30)
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_DEATH")
    private Date dateOfDeath;

    @Column(name = "GENDER", length = 1)
    private String gender;

    @Column(name = "RACE", length = 2)
    private String race;

    @Column(name = "PROFILE_PHOTO_PATH", length = 100)
    private String profilePhotoPath;

    @Column(name = "USERNAME", length = 50)
    private String username;

    @Column(name = "PASSWORD", length = 20)
    private String password;

    @Column(name = "SSN", length = 10)
    private String ssn;

    @Column(name = "ACCOUNT_STATUS", length = 1)
    private String accountStatus;

    @Column(name = "DATE_LAST_LOGON")
    private Date dateLastLogon;

    @Column(name = "TIMES_LOGON")
    private Integer timesLogon;

    @Column(name = "USER_CODE", length = 20)
    private String userCode;
    
    @ManyToMany(fetch = FetchType.EAGER)  // Load roles eagerly when fetching the user
    @JoinTable(
        name = "SM_USER_ROLE",
        joinColumns = @JoinColumn(name = "USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<SkRole> roles;

    // Getters and setters

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getProfilePhotoPath() {
		return profilePhotoPath;
	}

	public void setProfilePhotoPath(String profilePhotoPath) {
		this.profilePhotoPath = profilePhotoPath;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Date getDateLastLogon() {
		return dateLastLogon;
	}

	public void setDateLastLogon(Date dateLastLogon) {
		this.dateLastLogon = dateLastLogon;
	}

	public Integer getTimesLogon() {
		return timesLogon;
	}

	public void setTimesLogon(Integer timesLogon) {
		this.timesLogon = timesLogon;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Set<SkRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SkRole> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountStatus, dateLastLogon, dateOfBirth, dateOfDeath, firstName, gender, lastName,
				password, profilePhotoPath, race, roles, secondName, ssn, thirdName, timesLogon, userCode, userId,
				username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SmUser other = (SmUser) obj;
		return Objects.equals(accountStatus, other.accountStatus) && Objects.equals(dateLastLogon, other.dateLastLogon)
				&& Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(dateOfDeath, other.dateOfDeath)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(gender, other.gender)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(profilePhotoPath, other.profilePhotoPath) && Objects.equals(race, other.race)
				&& Objects.equals(roles, other.roles) && Objects.equals(secondName, other.secondName)
				&& Objects.equals(ssn, other.ssn) && Objects.equals(thirdName, other.thirdName)
				&& Objects.equals(timesLogon, other.timesLogon) && Objects.equals(userCode, other.userCode)
				&& Objects.equals(userId, other.userId) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "SmUser [userId=" + userId + ", firstName=" + firstName + ", secondName=" + secondName + ", thirdName="
				+ thirdName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", dateOfDeath=" + dateOfDeath
				+ ", gender=" + gender + ", race=" + race + ", profilePhotoPath=" + profilePhotoPath + ", username="
				+ username + ", password=" + password + ", ssn=" + ssn + ", accountStatus=" + accountStatus
				+ ", dateLastLogon=" + dateLastLogon + ", timesLogon=" + timesLogon + ", userCode=" + userCode
				+ ", roles=" + roles + "]";
	}
    
}
