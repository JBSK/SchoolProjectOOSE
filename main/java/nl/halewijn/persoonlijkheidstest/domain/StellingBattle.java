package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "StellingBattle")
public class StellingBattle extends Vraag {
	
	@OneToOne
	@JoinColumn(name = "eersteStelling", referencedColumnName = "stellingNummer", insertable = false, updatable = false)
	private Stelling eersteStelling;
	
	@OneToOne
	@JoinColumn(name = "tweedeStelling", referencedColumnName = "stellingNummer", insertable = false, updatable = false)
	private Stelling tweedeStelling;
	
	public StellingBattle(Stelling eersteStelling, Stelling tweedeStelling, String vraag) {
		super(vraag);
		this.eersteStelling = eersteStelling;
		this.tweedeStelling = tweedeStelling;
	}
	
	public StellingBattle() {
		super("");
	}

	public Stelling getEersteStelling() {
		return eersteStelling;
	}

	public void setEersteStelling(Stelling eersteStelling) {
		this.eersteStelling = eersteStelling;
	}

	public Stelling getTweedeStelling() {
		return tweedeStelling;
	}

	public void setTweedeStelling(Stelling tweedeStelling) {
		this.tweedeStelling = tweedeStelling;
	}
}
