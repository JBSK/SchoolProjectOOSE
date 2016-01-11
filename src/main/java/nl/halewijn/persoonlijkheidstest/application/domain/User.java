package nl.halewijn.persoonlijkheidstest.application.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "User")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

    @Id
    @GeneratedValue
    private int id;

	@NotNull
    @Column(unique = true)
	private String emailAddress;

    @NotNull
    private String passwordHash;

    @NotNull
    private Date dateRegisteredOn;

    @NotNull
    private boolean isAdmin = false;

    @NotNull
    private boolean isBanned = false;

	public User(String emailAddress, boolean isAdmin) {
        this.emailAddress = emailAddress.toLowerCase();
		this.dateRegisteredOn = new Date();
        this.isAdmin = isAdmin;
	}

	public User() {
        /*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
	}

    public int getId() {
        return id;
    }

    public String getEmailAddress() {
		return emailAddress.toLowerCase();
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress.toLowerCase();
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

    public Date getDateRegisteredOn() {
        return dateRegisteredOn;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}