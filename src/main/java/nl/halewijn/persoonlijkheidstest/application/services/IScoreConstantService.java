package nl.halewijn.persoonlijkheidstest.application.services;

import nl.halewijn.persoonlijkheidstest.application.domain.ScoreConstant;

public interface IScoreConstantService {

	ScoreConstant findByAnswer(char answer);

}
