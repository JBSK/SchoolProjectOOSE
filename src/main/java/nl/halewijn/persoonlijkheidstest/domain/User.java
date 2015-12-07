package nl.halewijn.persoonlijkheidstest.domain;

import ch.qos.logback.core.joran.spi.NoAutoStart;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "User")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

	@Id
	private String emailAddress;

    @NotNull
    private String passwordHash;

	public User(String emailAddress) {
        this.emailAddress = emailAddress;
	}
	
	public User() {  }

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getPassword() {
		return passwordHash;
	}
	
	public void setPassword(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}