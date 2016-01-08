package nl.halewijn.persoonlijkheidstest.domain;

import nl.halewijn.persoonlijkheidstest.services.CustomLogger;

import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;

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
	
	@Transient
	private PersonalityType primaryType;
	
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

    /**
     * Reformats the date that we retrieve from the database to a prettier format.
     * Used by myResults.html
     */
	public String getFormattedDate() {
		String unformattedDate = this.getDate().toString();
		String formattedDate = unformattedDate;
		try {
			Date simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(unformattedDate);
			formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(simpleDate);
		} catch (Exception e) {
            new CustomLogger().log(e);
		}
		return formattedDate;
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

	public List<Answer> getTestResultAnswers() {
		return testResultAnswers;
	}

	public void setTestResultAnswers(List<Answer> testResultAnswers) {
		this.testResultAnswers = testResultAnswers;
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

	public PersonalityType getPrimaryType() {
		return primaryType;
	}

	public void setPrimaryType(PersonalityType primaryType) {
		this.primaryType = primaryType;
	}
}
