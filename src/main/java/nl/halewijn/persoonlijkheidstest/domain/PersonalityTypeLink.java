package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PersonalityTypeLink")
public class PersonalityTypeLink {

	@Id
	@GeneratedValue
	private int linkId;

    @OneToOne
    @JoinColumn(name = "personalityType")
	private PersonalityType personalityType;

	@NotNull
	@Column(columnDefinition = "VARCHAR(1500)")
	private String url;

	public PersonalityTypeLink(PersonalityType personalityType, String url) {
		this.personalityType = personalityType;
		this.url = url;
	}

	public PersonalityTypeLink() {
        /*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
	}

    // TODO: Repo en Service?
    // TODO: QuestionnaireController -> /showLinks

	public PersonalityType getPersonalityType() {
		return personalityType;
	}

	public void setPersonalityType(PersonalityType personalityType) {
		this.personalityType = personalityType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}