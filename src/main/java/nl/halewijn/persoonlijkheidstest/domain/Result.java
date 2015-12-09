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

	public int getId() {
		return resultId;
	}

	public void setId(int id) {
		this.resultId = id;
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
}
