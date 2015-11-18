package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OpenQuestion")
public class OpenQuestion extends Question {

	@ManyToOne
	private PersonalityType personalityType;

	public OpenQuestion(PersonalityType personalityType, String questionText) {
		super(questionText);
		this.personalityType = personalityType;
	}

}