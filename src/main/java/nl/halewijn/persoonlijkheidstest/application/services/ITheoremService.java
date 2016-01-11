package nl.halewijn.persoonlijkheidstest.application.services;

import java.util.List;

import nl.halewijn.persoonlijkheidstest.application.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.application.domain.Theorem;

public interface ITheoremService {

	List<Theorem> getAllByPersonalityType(PersonalityType type);

}
