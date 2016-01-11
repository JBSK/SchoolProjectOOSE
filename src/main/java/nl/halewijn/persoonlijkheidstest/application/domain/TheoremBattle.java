package nl.halewijn.persoonlijkheidstest.application.domain;

import java.util.Date;

import javax.persistence.*;

@Entity
public class TheoremBattle extends Question {
	
	@OneToOne
    @JoinColumn(name = "firstTheorem")
	private Theorem firstTheorem;
	
	@OneToOne
    @JoinColumn(name = "secondTheorem")
	private Theorem secondTheorem;
	
	@Transient
	private char answer;
	
	public TheoremBattle(String questionText, Theorem firstTheorem, Theorem secondTheorem) {
		super(questionText);
		super.type = "TheoremBattle";
		this.firstTheorem = firstTheorem;
		this.secondTheorem = secondTheorem;
	}
	
	public TheoremBattle() {
		super("");
		super.type = "TheoremBattle";
	}

	public Theorem getFirstTheorem() {
		return firstTheorem;
	}

	public Theorem getSecondTheorem() {
		return secondTheorem;
	}
	
	public char getAnswer() {
		return answer;
	}

	public void setAnswer(char answer) {
		this.answer = answer;
		super.setDateAnswered(new Date());
	}

}
