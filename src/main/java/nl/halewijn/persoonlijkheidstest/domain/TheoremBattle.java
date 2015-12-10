package nl.halewijn.persoonlijkheidstest.domain;

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
		this.firstTheorem = firstTheorem;
		this.secondTheorem = secondTheorem;
	}
	
	public TheoremBattle() {
		super("");
	}

	public Theorem getFirstTheorem() {
		return firstTheorem;
	}

	public void setFirstTheorem(Theorem firstTheorem) {
		this.firstTheorem = firstTheorem;
	}

	public Theorem getSecondTheorem() {
		return secondTheorem;
	}

	public void setSecondTheorem(Theorem secondTheorem) {
		this.secondTheorem = secondTheorem;
	}
	
	
	public char getAnswer() {
		return answer;
	}

	public void setAnswer(char answer) {
		this.answer = answer;
	}

	public String getType() {
		return "TheoremBattle";	
	}
}
