package nl.halewijn.persoonlijkheidstest.services;

import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;

import java.util.List;

public interface IObjectService<O> {

	public void save(O o);
	public void delete(O o);
	public List<O> getAll();
	public O getById(int id);
	public void update(O o);
}
