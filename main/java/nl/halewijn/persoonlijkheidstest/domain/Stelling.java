package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Stelling")
public class Stelling {

	@Id
	@GeneratedValue
	private int stellingNummer;
	
	String stelling;
	double weging;
	
	public Stelling(String stelling, double weging) {
		this.stelling = stelling;
		this.weging = weging;
	}
	
	public Stelling() {
	}

	public String getStelling() {
		return stelling;
	}

	public void setStelling(String stelling) {
		this.stelling = stelling;
	}

	public double getWeging() {
		return weging;
	}

	public void setWeging(double weging) {
		this.weging = weging;
	}
	
}
