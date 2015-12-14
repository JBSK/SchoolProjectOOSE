package nl.halewijn.persoonlijkheidstest.services;

import nl.halewijn.persoonlijkheidstest.domain.ScoreConstant;

public interface IScoreConstantService {

	ScoreConstant findByAnswer(char answer);

}
