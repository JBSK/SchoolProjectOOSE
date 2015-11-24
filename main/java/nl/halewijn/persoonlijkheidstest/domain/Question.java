package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int questionID;

    @NotNull
    @Column(name="text", columnDefinition="varchar(255) default 'Kies de stelling die het meest voor u van toepassing is'")
    private String text;
	
	public Question(String questionText) {
        this.text = questionText;
	}

	public int getID() {
		return questionID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getClassName() {
		return getClass().getName();
		
	}

}