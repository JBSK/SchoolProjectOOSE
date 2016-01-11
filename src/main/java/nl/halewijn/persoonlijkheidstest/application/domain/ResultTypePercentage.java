package nl.halewijn.persoonlijkheidstest.application.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ResultTypePercentage")
public class ResultTypePercentage implements Serializable {
	
	@Id
	@GeneratedValue
	private int resultTypePercentageId;
	
	@OneToOne
    @JoinColumn(name = "result")
	private Result result;
	
	@OneToOne
    @JoinColumn(name = "personalityType")
	private PersonalityType personalityType;
	
	private double percentage;
	
	public ResultTypePercentage(Result result, PersonalityType personalityType, double percentage) {
		this.result = result;
		this.personalityType = personalityType;
		this.percentage = percentage;
	}
	
	public ResultTypePercentage() {
		/*
		 * UserController calls an Answer ArrayList, which apparently requires an empty constructor.
		 */
	}
	
	public Result getResult() {
		return result;
	}
	
	public double getPercentage() {
		return percentage;
	}

	public PersonalityType getPersonalityType() {
		return personalityType;
	}

	public void setPersonalityType(PersonalityType personalityType) {
		this.personalityType = personalityType;
	}
	
}
