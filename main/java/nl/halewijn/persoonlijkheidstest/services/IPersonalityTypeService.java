package nl.halewijn.persoonlijkheidstest.services;

import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;

import java.util.List;

public interface IPersonalityTypeService {

	public void save(PersonalityType personalityType);
	public void delete(PersonalityType personalityType);
	public List<PersonalityType> getAll();
	public PersonalityType getById(int id);
	public void update(PersonalityType personalityType);
}
