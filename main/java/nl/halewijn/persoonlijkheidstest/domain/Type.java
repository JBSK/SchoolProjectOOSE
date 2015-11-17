package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Type")
public class Type {

	@Id
	@GeneratedValue
	private int typeNummer;
	
	private String typeNaam;

	public Type() {
	}
	
	
}
