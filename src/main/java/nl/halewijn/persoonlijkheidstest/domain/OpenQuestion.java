package nl.halewijn.persoonlijkheidstest.domain;

import java.util.Date;

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
		super.setDateAnswered(new Date());
	}

	public String getType() {
		return "OpenQuestion";	
	}

}