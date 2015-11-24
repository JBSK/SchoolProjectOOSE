package nl.halewijn.persoonlijkheidstest.services;

import java.util.List;

import nl.halewijn.persoonlijkheidstest.domain.Theorem;

public interface ITheoremService {

	public void save(Theorem theorem);
	public void delete(Theorem theorem);
	public List<Theorem> getAll();
	public Theorem getById(int id);
	public void update(Theorem theorem);
}
