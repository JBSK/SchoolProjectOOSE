package nl.halewijn.persoonlijkheidstest.domain;

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

@Entity
@Table(name = "Result")
public class Result {
	
	@Id
	@GeneratedValue
	private int id;
	
	@OneToOne
    @JoinColumn(name = "user")
	private User user;
	
	private Date date;
	
	private double weight2_score;
	private double weight3_score;
	private double weight4_score;
	
	@ManyToMany
    @JoinTable(
            name="Result_Answers",
            joinColumns=@JoinColumn(name="result_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="answer_id", referencedColumnName="id")
    )
	private List<Answer> testResultAnswers = new ArrayList<Answer>();

	public Result(User user) {
		this.user = user;
		this.date = new Date();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public double getWeight2_score() {
		return weight2_score;
	}

	public void setWeight2_score(double weight2_score) {
		this.weight2_score = weight2_score;
	}

	public double getWeight3_score() {
		return weight3_score;
	}

	public void setWeight3_score(double weight3_score) {
		this.weight3_score = weight3_score;
	}

	public double getWeight4_score() {
		return weight4_score;
	}

	public void setWeight4_score(double weight4_score) {
		this.weight4_score = weight4_score;
	}
}
