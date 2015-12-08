package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class OpenQuestion extends Question {

	@Transient
	private String answer;
	
	public OpenQuestion(String questionText) {
		super(questionText);
	}
	
	public OpenQuestion() {
		super("");
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getType() {
		return "OpenQuestion";	
	}

}