package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PersonalityType")
public class PersonalityType {

	@Id
	@GeneratedValue
	private int typeID;

	@NotNull
	private String name;

	@NotNull
	private String primaryDescription;

	@NotNull
	private String secondaryDescription;

	public PersonalityType(String name, String primaryDescription, String secondaryDescription) {
		this.name = name;
		this.primaryDescription = primaryDescription;
		this.secondaryDescription = secondaryDescription;
	}

	public PersonalityType() {
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimaryDescription() {
		return primaryDescription;
	}

	public void setPrimaryDescription(String primaryDescription) {
		this.primaryDescription = primaryDescription;
	}

	public String getSecondaryDescription() {
		return secondaryDescription;
	}

	public void setSecondaryDescription(String secondaryDescription) {
		this.secondaryDescription = secondaryDescription;
	}
	
	
	
	
}