package nl.halewijn.persoonlijkheidstest.services;

import java.util.List;

import nl.halewijn.persoonlijkheidstest.domain.Answer;
import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.domain.ResultTypePercentage;

public interface IResultService {

	Long countUserTests();

	Long countAnonymousTests();

	Long count();

	List<Result> findAll();

	void saveResultTypePercentage(ResultTypePercentage resultTypePercentage);

	Result saveResult(Result testResult);

	Answer saveAnswer(Answer testResultAnswer);

}
