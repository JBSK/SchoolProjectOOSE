package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OpenVraag")
public class OpenVraag extends Vraag {

	public OpenVraag(String vraag) {
		super(vraag);
	}
	
	public OpenVraag() {
		super("");
	}
	
	

}
