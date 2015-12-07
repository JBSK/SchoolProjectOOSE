package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Theorem")
public class Theorem {

	@Id
	@GeneratedValue
	private int theoremID;

    @OneToOne
    @JoinColumn(name = "personalityType")
    private PersonalityType personalityType;

	@NotNull
	private String text;

    @NotNull
	private double weight;

	@NotNull
	private double subWeight1;

	@NotNull
	private double subWeight2;

	@NotNull
	private double subWeight3;
	
	public Theorem(PersonalityType personalityType, String text, double weight, double subWeight1, double subWeight2, double subWeight3) {
		this.personalityType = personalityType;
        this.text = text;
		this.weight = weight;
		this.subWeight1 = subWeight1;
		this.subWeight2 = subWeight2;
		this.subWeight3 = subWeight3;
	}

	public Theorem() {
        /*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
	}

	public int getTheoremID() {
		return theoremID;
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

	public double getSubWeight1() {
		return subWeight1;
	}

	public void setSubWeight1(int subWeight1) {
		this.subWeight1 = subWeight1;
	}

	public double getSubWeight2() {
		return subWeight2;
	}

	public void setSubWeight2(int subWeight2) {
		this.subWeight2 = subWeight2;
	}

	public double getSubWeight3() {
		return subWeight3;
	}

	public void setSubWeight3(int subWeight3) {
		this.subWeight3 = subWeight3;
	}
}