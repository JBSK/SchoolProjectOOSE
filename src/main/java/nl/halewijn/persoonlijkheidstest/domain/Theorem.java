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
	
	public Theorem(PersonalityType personalityType, String text, double weight) {
		this.personalityType = personalityType;
        this.text = text;
		this.weight = weight;
	}
	
	public Theorem() {
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
	
}