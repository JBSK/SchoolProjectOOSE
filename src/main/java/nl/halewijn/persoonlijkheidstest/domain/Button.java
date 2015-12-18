package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Buttons")
public class Button {
	
	@Id
	@GeneratedValue
	private int buttonId;

	@NotNull
	private String buttonText;

	@NotNull
	private String buttonDescription;
	
	public int getButtonId() {
		return this.buttonId;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public String getButtonDescription() {
		return buttonDescription;
	}

	public void setButtonDescription(String buttonDescription) {
		this.buttonDescription = buttonDescription;
	}
}