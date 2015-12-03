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
}
