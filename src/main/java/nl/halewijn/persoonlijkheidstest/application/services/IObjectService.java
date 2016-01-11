package nl.halewijn.persoonlijkheidstest.application.services;

import java.util.List;

public interface IObjectService<O> {

	O save(O o);
	void delete(O o);
	List<O> getAll();
	O getById(int id);

}
