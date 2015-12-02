package nl.halewijn.persoonlijkheidstest.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Questionnaire;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;

public interface ITheoremService {

	void save(Theorem theorem);

	void delete(Theorem theorem);

	List<Theorem> getAll();

	Theorem getById(int id);

	void update(Theorem theorem);


}
