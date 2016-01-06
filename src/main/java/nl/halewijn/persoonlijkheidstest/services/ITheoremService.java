package nl.halewijn.persoonlijkheidstest.services;

import java.util.List;

import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;

public interface ITheoremService {

	List<Theorem> getAllByPersonalityType(PersonalityType type);

}
