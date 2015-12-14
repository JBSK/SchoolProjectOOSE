package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PersonalityType")
public class PersonalityType {

	@Id
	@GeneratedValue
	private int typeId;

	@NotNull
	private String name;

	@NotNull
	@Column(columnDefinition = "VARCHAR(1500)")
	private String primaryDescription;

	@NotNull
	@Column(columnDefinition = "VARCHAR(1500)")
	private String secondaryDescription;

	public PersonalityType(String name, String primaryDescription, String secondaryDescription) {
		this.name = name;
		this.primaryDescription = primaryDescription;
		this.secondaryDescription = secondaryDescription;
	}

	public PersonalityType() {
        /*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
	}

	public int getTypeID() {
		return typeId;
	}

	public void setTypeID(int typeID) {
		this.typeId = typeID;
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