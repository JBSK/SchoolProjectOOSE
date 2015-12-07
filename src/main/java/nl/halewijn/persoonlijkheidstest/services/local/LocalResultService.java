package nl.halewijn.persoonlijkheidstest.services.local;


import nl.halewijn.persoonlijkheidstest.datasource.repository.AnswerRepository;
import nl.halewijn.persoonlijkheidstest.datasource.repository.ResultRepository;
import nl.halewijn.persoonlijkheidstest.datasource.repository.ResultTypePercentageRepository;
import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.domain.ResultTypePercentage;
import nl.halewijn.persoonlijkheidstest.domain.Answer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalResultService  {

	@Autowired
	private ResultRepository testResultRepository;
	
	@Autowired
	private AnswerRepository testResultAnswerRepository;
	
	@Autowired
	private ResultTypePercentageRepository resultTypePercentageRepository;
	
	public Answer saveAnswer(Answer testResultAnswer) {
		return testResultAnswerRepository.save(testResultAnswer);
	}
	
	public Result saveResult(Result testResult) {
		return testResultRepository.save(testResult);
	}

	public void saveResultTypePercentage(ResultTypePercentage resultTypePercentage) {
		resultTypePercentageRepository.save(resultTypePercentage);
	}

	public List<Result> findAll() {
		return testResultRepository.findAll();
	}

	public Long count() {
		return testResultRepository.count();
	}

	public Long countUserTests() {
		return testResultRepository.countUserTests();
	}

	public Long countAnonymousTests() {
		return testResultRepository.countAnonymousTests();
	}
}