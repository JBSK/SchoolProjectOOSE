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
}