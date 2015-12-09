package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Answer")
public class Answer {

	@Id
	@GeneratedValue
	private int answerId;
	
	@OneToOne
    @JoinColumn(name = "question")
	private Question question;
	
	private String answer;
	
	public Answer(Question question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public int getId() {
		return answerId;
	}

	public void setId(int id) {
		this.answerId = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
