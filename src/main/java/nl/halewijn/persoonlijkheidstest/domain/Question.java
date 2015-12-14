package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "Question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Question {

	@Id
	@GeneratedValue
	private int questionId;

    @NotNull
    @Column(name="text", columnDefinition="varchar(255) default 'Kies de stelling die het meest voor u van toepassing is'")
    private String text;

	@Transient
	private Date dateAnswered;
	
	public Question(String questionText) {
        this.text = questionText;
		this.dateAnswered = new Date();
	}

	public Date getDateAnswered() {
		return dateAnswered;
	}

	public int getQuestionId() {
		return questionId;
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

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

}