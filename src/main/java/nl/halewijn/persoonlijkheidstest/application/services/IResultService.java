package nl.halewijn.persoonlijkheidstest.application.services;

import java.util.List;

import nl.halewijn.persoonlijkheidstest.application.domain.Answer;
import nl.halewijn.persoonlijkheidstest.application.domain.Result;
import nl.halewijn.persoonlijkheidstest.application.domain.ResultTypePercentage;

public interface IResultService {

	Long countUserTests();

	Long countAnonymousTests();

	Long count();

	List<Result> findAll();

	void saveResultTypePercentage(ResultTypePercentage resultTypePercentage);

	Result saveResult(Result testResult);

	Answer saveAnswer(Answer testResultAnswer);

	List<Answer> findAllAnswers();

	List<ResultTypePercentage> findAllResultTypePercentages();

	Answer findAnswer(int id);

	List<ResultTypePercentage> findResultTypePercentageByResult(Result result);
}
