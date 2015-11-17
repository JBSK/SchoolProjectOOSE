package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Vraag")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Vraag {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int vraagNummer;
	
	private String vraag;
	
	@ManyToOne
	@JoinColumn(name = "typeNummer")
	private Type type;
	
	public Vraag(String vraag) {
		this.vraag = vraag;
	}
	
	public Vraag() {

	}
	
}
