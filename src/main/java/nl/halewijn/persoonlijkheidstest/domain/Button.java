package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Buttons")
public class Button {
	
	@Id
	@GeneratedValue
	private int buttonId;
	
	private String buttonText;
	
	private String buttonDescription;
	
	public Button() {
		
	}
	
	public int getButtonId() {
		return this.buttonId;
	}
	
	public void setButtonId(int buttonId) {
		this.buttonId = buttonId;
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