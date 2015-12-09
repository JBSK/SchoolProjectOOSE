package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Theorem")
public class Theorem {

	@Id
	@GeneratedValue
	private int theoremId;

    @OneToOne
    @JoinColumn(name = "personalityType")
    private PersonalityType personalityType;

	@NotNull
	private String text;

    @NotNull
	private double weight;

	@NotNull
	private double denial;

	@NotNull
	private double recognition;

	@NotNull
	private double development;
	
	public Theorem(PersonalityType personalityType, String text, double weight, double denial, double recognition, double development) {
		this.personalityType = personalityType;
        this.text = text;
		this.weight = weight;
		this.denial = denial;
		this.recognition = recognition;
		this.development = development;
	}

	public Theorem() {
        /*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
	}

	public int getTheoremID() {
		return theoremId;
	}

    public PersonalityType getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(PersonalityType personalityType) {
        this.personalityType = personalityType;
    }

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getDenial() {
		return denial;
	}

	public void setDenial(double denial) {
		this.denial = denial;
	}

	public double getRecognition() {
		return recognition;
	}

	public void setRecognition(double recognition) {
		this.recognition = recognition;
	}

	public double getDevelopment() {
		return development;
	}

	public void setDevelopment(double development) {
		this.development = development;
	}
}