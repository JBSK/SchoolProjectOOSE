package nl.halewijn.persoonlijkheidstest.application.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

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

	private Date dateAnswered;
	
	public Answer(Question question, String answer, Date dateAnswered) {
		this.question = question;
		this.answer = answer;
        this.dateAnswered = dateAnswered;
	}
	
	public Answer() {
		/*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
	}
	
	public int getId() {
		return answerId;
	}
	
	public Question getQuestion() {
		return question;
	}
}
