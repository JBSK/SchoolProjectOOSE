package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "Question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Question {

	@Id
	@GeneratedValue
	private int questionId;

    @NotNull
    @Column(name="text", columnDefinition="varchar(255) default 'Kies de stelling die het meest voor u van toepassing is'")
    private String text;

	@Transient
	private Date dateAnswered;

	@Transient
	protected String type;

	private boolean active = true;

	public Question(String questionText) {
        this.text = questionText;
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

	public void setDateAnswered(Date dateAnswered) {
		this.dateAnswered = dateAnswered;
	}

	public String getType() {
		return type;
	}

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}