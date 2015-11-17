package nl.halewijn.persoonlijkheidstest.services;

import java.util.List;

import org.springframework.stereotype.Service;

import nl.halewijn.persoonlijkheidstest.domain.Stelling;

public interface IStellingService {

	public void save(Stelling stelling);
	public void delete(Stelling stelling);
	public List<Stelling> getAll();
	public Stelling getById(long id);
	public void update(Stelling stelling);
}
