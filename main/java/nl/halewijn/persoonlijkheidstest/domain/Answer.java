package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Answer")
public class Answer {

	@Id
	@GeneratedValue
	private int answerID;

	//One or the other must not be null...
	private char answerChar;
	private String answerString;

    @NotNull
	private int points;

	public Answer(char answer, int points) {
        this.answerChar = answer;
        this.points = points;
    }

    public Answer(String answer, int points) {
        this.answerString = answer;
        this.points = points;
    }

	public int getAnswerID() {
		return answerID;
	}

    public char getAnswerChar() {
        return answerChar;
    }

    public void setAnswerChar(char answerChar) {
        this.answerChar = answerChar;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}