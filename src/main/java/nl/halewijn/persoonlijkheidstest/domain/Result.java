package nl.halewijn.persoonlijkheidstest.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Result")
public class Result {
	
	@Id
	@GeneratedValue
	private int resultId;
	
	@OneToOne
    @JoinColumn(name = "user")
	private User user;
	
	private Date date;
	
	private double scoreDenial;
	private double scoreRecognition;
	private double scoreDevelopment;
	
	@ManyToMany
    @JoinTable(
            name="Result_Answers",
            joinColumns=@JoinColumn(name="result_id", referencedColumnName="resultId"),
            inverseJoinColumns=@JoinColumn(name="answer_id", referencedColumnName="answerId")
    )
	private List<Answer> testResultAnswers = new ArrayList<>();

	public Result(User user) {
		this.user = user;
		this.date = new Date();

	}

	public Result() {
		/*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
	}

	public int getId() {
		return resultId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void addTestResultAnswer(Answer testResultAnswer) {
		testResultAnswers.add(testResultAnswer);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getScoreDenial() {
		return scoreDenial;
	}

	public void setScoreDenial(double scoreDenial) {
		this.scoreDenial = scoreDenial;
	}

	public double getScoreRecognition() {
		return scoreRecognition;
	}

	public void setScoreRecognition(double scoreRecognition) {
		this.scoreRecognition = scoreRecognition;
	}

	public double getScoreDevelopment() {
		return scoreDevelopment;
	}

	public void setScoreDevelopment(double scoreDevelopment) {
		this.scoreDevelopment = scoreDevelopment;
	}
}
