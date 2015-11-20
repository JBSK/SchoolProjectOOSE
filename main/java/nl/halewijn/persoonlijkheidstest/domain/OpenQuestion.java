package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OpenQuestion")
public class OpenQuestion extends Question {

	public OpenQuestion(String questionText) {
		super(questionText);
	}
	
	public OpenQuestion() {
		super("");
	}
	
	public String getText() {
		return super.getText();
	}

}